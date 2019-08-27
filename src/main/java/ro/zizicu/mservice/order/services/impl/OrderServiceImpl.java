package ro.zizicu.mservice.order.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.data.ProductRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.exceptions.OrderAlreadyShipped;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	@Transactional
	public Order createOrder(Order order, 
						  List<ProductValueObject> productIds, 
						  Employee employee, 
						  Customer customer,
						  Shipper shipper) throws ProductNotFoundException {
		if(logger.isInfoEnabled()) logger.info("create order");
		
		if(logger.isDebugEnabled()) logger.debug("number of order details: " + productIds.size());
		for(ProductValueObject pvo : productIds)
		{
			Optional<Product> o = productRepository.findById(pvo.productId);
			if(!o.isPresent())
				throw new ProductNotFoundException("product id = " + pvo.productId);
			
			Product p = productRepository.findById(pvo.productId).get();
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(p);
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
		order.setShipper(shipper);
		order = orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order created");
		return order;
	}

	@Override
	@Transactional
	public void updateOrder(Order order, List<ProductValueObject> productIds) throws ProductNotFoundException {
		if(logger.isInfoEnabled()) logger.info("update order");
		List<OrderDetail> orderDetails = new ArrayList<>();
		for(ProductValueObject pvo : productIds)
		{
			Optional<Product> o = productRepository.findById(pvo.productId);
			if(!o.isPresent())
				throw new ProductNotFoundException("product id = " + pvo.productId);
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(o.get());
			orderDetail.setOrder(order);
			orderDetail.setQuantity(pvo.quantity);
			orderDetail.setUnitPrice(pvo.unitPrice);
			orderDetail.setDiscount(pvo.discount);
			orderDetails.add(orderDetail);
			order.getOrderDetails().add(orderDetail);
		}
		orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order updated");
	}

	@Override
	@Transactional
	public void cancelOrder(Order order) {
		if(logger.isInfoEnabled()) logger.info("cancel order " + order.getId());
		orderRepository.delete(order);
		if(logger.isInfoEnabled()) logger.info("order cancelled");
	}

	@Override
	public Order loadOrder(Integer id) throws OrderNotFoundException {
		Optional<Order> o = orderRepository.findById(id);
		if(!o.isPresent())
			throw new OrderNotFoundException(id.toString());
		return o.get();
	}

	@Override
	public List<Order> findOrders(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findOrders(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findOrders(String countryToShip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findOrders(Employee createdBy) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteOrder(Order order) throws OrderAlreadyShipped {
		if(order.getShippedDate() != null) 
			throw new OrderAlreadyShipped(order.getId());
		orderRepository.delete(order);
	}
}
