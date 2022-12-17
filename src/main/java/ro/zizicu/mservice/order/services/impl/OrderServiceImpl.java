package ro.zizicu.mservice.order.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.NotEnoughQuantity;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.restclient.RestClient;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.mservice.order.services.distributed.transaction.AddOrder;
import ro.zizicu.nwbase.service.DistributedTransactionService;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;
import ro.zizicu.nwbase.transaction.TransactionMessage;

@Service
@Slf4j
public class OrderServiceImpl extends CrudServiceImpl<Order, Integer>
	implements OrderService {

	private final RestClient restClient;
	private final OrderRepository orderRepository;
	private final DistributedTransactionService distributedTransactionService;
	private AddOrder addOrder;
	
	public OrderServiceImpl(OrderRepository orderRepository,
							CustomerRepository customerRepository,
							DistributedTransactionService distributedTransactionService,
							AddOrder addOrder,
							RestClient restClient) {
		this.addOrder = addOrder;
		this.repository = orderRepository;
		this.orderRepository = orderRepository;
		this.distributedTransactionService = distributedTransactionService;
		this.restClient = restClient;
	}

	@Override
	public Order createOrder(Order order,
						  List<ProductValueObject> products,
						  Employee employee, 
						  Customer customer,
						  Integer shipperId) throws ProductNotFoundException {
		if(log.isInfoEnabled()) log.info("create order");
		if(log.isDebugEnabled()) log.debug("number of order details: " + products.size());
		Long transactionId = orderRepository.getTransactionId();
		log.debug("start distributed transaction {}", transactionId);

		order = addOrderDetails(products, order, transactionId);
		order.setOrderDate(new Date());
		order.setCustomer(customer);
		order.setEmployee(employee);
		order.setShipperId(shipperId);
		addOrder.setOrder(order);
		distributedTransactionService.executeTransactionStep(addOrder, transactionId);
		if(log.isInfoEnabled()) log.info("order created");
		return order;
	}

	@Override
	public Order update(Order order, List<ProductValueObject> products) {
		if(log.isInfoEnabled()) log.info("update order with id {}", order.getId());
		Order fromDatabase = orderRepository.findById(order.getId()).orElseThrow(OrderNotFoundException::new);
		fromDatabase.getOrderDetails().clear();
		Long transactionId = orderRepository.getTransactionId();
		log.debug("start distributed transaction {}", transactionId);

		if(order.getFreight()!= null)
			fromDatabase.setFreight(order.getFreight());
		if(order.getShipAddress()!= null)
			fromDatabase.setShipAddress(order.getShipAddress());
		
		fromDatabase = addOrderDetails(products, fromDatabase, transactionId);
		order = orderRepository.save(fromDatabase);
		if(log.isInfoEnabled()) log.info("order updated");
		return order;
	}

	@Override
	@Transactional
	public void cancelOrder(Order order) {
		if(log.isInfoEnabled()) log.info("cancel order " + order.getId());
		orderRepository.delete(order);
		if(log.isInfoEnabled()) log.info("order cancelled");
	}

	@Override
	public List<Order> findOrders(Customer customer, Date start, Date end, String countryToShip, Employee createdBy) {
		return orderRepository.findOrders(customer, start, end, createdBy);
	}

	private List<ProductValueObject> checkStock(List<ProductValueObject> products) {
		return products.stream()
				.map( v ->  restClient.checkStock(v.getId(), v.getQuantity()).get())
				.collect(Collectors.toList());
	}

	private Order addOrderDetails(List<ProductValueObject> products, Order order, Long transactionId ) {

		products = checkStock(products);

		List<ProductValueObject> insufficientStockProducts = products.stream()
				.filter( v -> !v.getEnoughStock() ).collect(Collectors.toList());

		if(!insufficientStockProducts.isEmpty() )
		{
			log.error("not enough stock ");
			String errorMessage = insufficientStockProducts.stream().
					map( v -> "" + v.getId() + " " + v.getUnitsInStock()  ).collect(Collectors.joining(" \n"));
			throw new NotEnoughQuantity(errorMessage);
		}

		products.forEach(p -> restClient.updateProductQuantity(p, transactionId));

		List<OrderDetail> orderDetails = products.stream().map( v -> OrderDetail.builder().productId(v.getId())
				.unitPrice(v.getUnitPrice())
				.quantity(v.getQuantity())
				.discount(v.getDiscount())
				.order(order)
				.build()
		 ).collect(Collectors.toList());
		order.setOrderDetails(orderDetails);
		return order;
	}

}
