package ro.zizicu.mservice.order.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.*;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.restclient.ProductServiceClient;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.nwbase.controller.request.UpdateStockRequest;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends CrudServiceImpl<Order, Integer>
	implements OrderService {

	private final ProductServiceClient productServiceClient;
	private final EmployeeRepository employeeRepository;
	private final CustomerRepository customerRepository;

	public OrderServiceImpl(OrderRepository orderRepository,
							EmployeeRepository employeeRepository,
							CustomerRepository customerRepository,
							ProductServiceClient productServiceClient
							) {
		super(orderRepository);
		this.employeeRepository = employeeRepository;
		this.customerRepository = customerRepository;
		this.productServiceClient = productServiceClient;
	}

	@Override
	@Transactional
	public Order createOrder(Order order,
						  List<ProductValueObject> products,
						  Integer employeeId,
						  String customerId,
						  Integer shipperId) throws ProductNotFoundException {
		if(log.isInfoEnabled()) log.info("create order");
		if(log.isDebugEnabled()) log.debug("number of order details: {}", products.size());
		Employee employee = employeeRepository.findById(employeeId).orElse(null);
		Customer customer = customerRepository.findById(customerId).orElse(null);
		order = addOrderDetails(products, order);
		order.setOrderDate(new Date());
		order.setCustomer(customer);
		order.setEmployee(employee);
		order.setShipperId(shipperId);
		order = getRepository().save(order);
		if(log.isInfoEnabled()) log.info("order created");
		return order;
	}

	@Override
	@Transactional
	public Order update(Order order, List<ProductValueObject> products) {
		if(log.isInfoEnabled()) log.info("update order with id {}", order.getId());
		Order fromDatabase = getRepository().findById(order.getId()).orElseThrow(OrderNotFoundException::new);
		fromDatabase.getOrderDetails().clear();
		if(order.getFreight()!= null)
			fromDatabase.setFreight(order.getFreight());
		if(order.getShipAddress()!= null)
			fromDatabase.setShipAddress(order.getShipAddress());
		log.debug("add details to order with id {}", order.getId());
		fromDatabase = addOrderDetails(products, fromDatabase);
		log.debug("save order with id {}", fromDatabase.getId());
		order = getRepository().save(fromDatabase);
		if(log.isInfoEnabled()) log.info("order updated");
		return order;
	}

	@Override
	@Transactional
	public void cancelOrder(Order order) {
		if(log.isInfoEnabled()) log.info("cancel order {}", order.getId());
		getRepository().delete(order);
		if(log.isInfoEnabled()) log.info("order cancelled");
	}

	@Override
	public List<Order> findOrders(String customerId, Date start, Date end, String countryToShip, Integer employeeId) {
		Customer customer = customerId == null ? null : customerRepository.findById(customerId).orElse(null);
		Employee employee = employeeId == null ? null : employeeRepository.findById(employeeId).orElse(null);
		return ((OrderRepository)getRepository()).findOrders(customer, start, end, employee);
	}

	private List<ProductValueObject> checkAndUpdateStock(List<ProductValueObject> products) {

		for(ProductValueObject productValueObject : products) {
			Product product = productServiceClient.getProductById(productValueObject.getId());
			if(product.getUnitsInStock() < productValueObject.getUnitsOnOrder())
				productValueObject.setEnoughStock(Boolean.FALSE);
			else {
				//  update stock
				UpdateStockRequest updateStockRequest = new UpdateStockRequest();
				updateStockRequest.setId(productValueObject.getId());
				updateStockRequest.setUnitsOnOrder(productValueObject.getUnitsOnOrder());
				productServiceClient.updateProductStock(updateStockRequest);
			}
		}

		return products;
	}

	private Order addOrderDetails(List<ProductValueObject> products, Order order) {
		products = checkAndUpdateStock(products);

		List<ProductValueObject> insufficientStockProducts = products.stream()
				.filter( v -> !v.getEnoughStock() ).toList();

		List<OrderDetail> orderDetails = products.stream().map(
				v -> {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setId( new OrderDetailId(order.getId(), v.getId()) );
				orderDetail.setQuantity(v.getUnitsOnOrder());
				orderDetail.setDiscount(v.getDiscount());
				orderDetail.setUnitPrice(v.getUnitPrice());
				orderDetail.setOrder(order);
				return orderDetail;
				}
		 ).collect(Collectors.toList());
		order.setOrderDetails(orderDetails);
		return order;
	}

	@Override
	protected Order transform(Order e) {
		Order order = new Order();
		BeanUtils.copyProperties(e, order, "shipperId", "customerId", "orderDetails");
		return order;
	}
}
