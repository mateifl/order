package ro.zizicu.mservice.order.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.restclient.ProductRestObject;
import ro.zizicu.mservice.order.restclient.RestClient;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends CrudServiceImpl<Order, Integer>
	implements OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final RestClient restClient;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	
	@Override
	@Transactional
	public Order createOrder(Order order, 
						  List<ProductValueObject> productIds, 
						  Employee employee, 
						  Customer customer,
						  Integer shipperId) throws ProductNotFoundException {
		if(logger.isInfoEnabled()) logger.info("create order");
		if(logger.isDebugEnabled()) logger.debug("number of order details: " + productIds.size());
		for(ProductValueObject pvo : productIds)
		{
			ProductRestObject o = restClient.loadAndUpdateProduct(pvo.productId, pvo.quantity);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductId(pvo.productId);
			orderDetail.setOrder(order);
			orderDetail.setQuantity(pvo.quantity);
			orderDetail.setUnitPrice(pvo.unitPrice);
			orderDetail.setDiscount(pvo.discount);
			order.getOrderDetails().add(orderDetail);
		}
		
		if(customer.getId() != null) {
			logger.info("Customer already in the database " + customer.getId() );
			customer = customerRepository.findById(customer.getId()).orElse(customer);
		}
		order.setCustomer(customer);
		order.setEmployee(employee);
		order.setShipperId(shipperId);
		order = orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order created");
		return order;
	}

	@Override
	@Transactional
	public Order update(Order order, List<ProductValueObject> productIds) {
		if(logger.isInfoEnabled()) logger.info("update order");
		Order fromDatabase = orderRepository.findById(order.getId()).orElseThrow(OrderNotFoundException::new);
		fromDatabase.getOrderDetails().clear();
		
		if(order.getFreight()!= null)
			fromDatabase.setFreight(order.getFreight());
		if(order.getShipAddress()!= null)
			fromDatabase.setShipAddress(order.getShipAddress());
		
		for(ProductValueObject pvo : productIds)
		{
			ProductRestObject o = restClient.loadAndUpdateProduct(pvo.productId, pvo.quantity);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductId(pvo.productId);
			orderDetail.setOrder(order);
			orderDetail.setQuantity(pvo.quantity);
			orderDetail.setUnitPrice(pvo.unitPrice);
			orderDetail.setDiscount(pvo.discount);
			order.getOrderDetails().add(orderDetail);
		}
		order = orderRepository.save(fromDatabase);
		if(logger.isInfoEnabled()) logger.info("order updated");
		return order;
	}

	@Override
	@Transactional
	public void cancelOrder(Order order) {
		if(logger.isInfoEnabled()) logger.info("cancel order " + order.getId());
		orderRepository.delete(order);
		if(logger.isInfoEnabled()) logger.info("order cancelled");
	}

	@Override
	public List<Order> findOrders(Customer customer, Date start, Date end, String countryToShip, Employee createdBy) {
		return orderRepository.findOrders(customer, start, end, createdBy);
	}
}
