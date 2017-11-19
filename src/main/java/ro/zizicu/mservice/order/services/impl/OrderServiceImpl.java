package ro.zizicu.mservice.order.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.data.ProductRepository;
import ro.zizicu.mservice.order.data.ShipperRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.services.OrderService;

@Service
public class OrderServiceImpl extends SimpleServiceImpl<OrderRepository, Order, Integer> implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private EmployeeRepository employeeRepository; 
	@Autowired
	private ShipperRepository shipperRepository;
	
	@Override
	@Transactional
	public void save(Order order, 
						  List<ProductValueObject> productIds, 
						  Integer employeeId, 
						  String customerCode,
						  Integer shipperId) {
		if(logger.isInfoEnabled()) logger.info("create order");
		
		List<OrderDetail> orderDetails = new ArrayList<>();
		if(logger.isDebugEnabled()) logger.debug("number of order details: " + productIds.size());
		for(ProductValueObject pvo : productIds)
		{
			Product p = productRepository.findOne(pvo.getId());
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProduct(p);
			orderDetail.setOrder(order);
			orderDetail.setQuantity(pvo.getQuantity());
			orderDetail.setUnitPrice(pvo.getUnitPrice());
			orderDetail.setDiscount(pvo.getDiscount());
			orderDetails.add(orderDetail);
		}
		
		order.setOrderDetails(orderDetails);
		if(customerCode != null && !customerCode.isEmpty()) {
			Customer customer = customerRepository.findOne(customerCode);
			order.setCustomer(customer);
		}
		if(employeeId != null) {
			Employee employee = employeeRepository.findOne(employeeId);
			order.setEmployee(employee);
		}
		if(shipperId != null) {
			Shipper shipper = shipperRepository.findOne(shipperId);
			order.setShipper(shipper);
		}
		orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order created");
		
	}

//	@Override
//	@Transactional
//	public void updateOrder(Order order, List<ProductValueObject> productIds) {
//		if(logger.isInfoEnabled()) logger.info("update order");
//		List<OrderDetail> orderDetails = new ArrayList<>();
//		for(ProductValueObject pvo : productIds)
//		{
//			Product p = productRepository.findOne(pvo.getId());
//			OrderDetail orderDetail = new OrderDetail();
//			orderDetail.setProduct(p);
//			orderDetail.setOrder(order);
//			orderDetail.setQuantity(pvo.getQuantity());
//			orderDetail.setUnitPrice(pvo.getUnitPrice());
//			orderDetail.setDiscount(pvo.getDiscount());
//			orderDetails.add(orderDetail);
//		}
//		order.setOrderDetails(orderDetails);
//		orderRepository.save(order);
//		if(logger.isInfoEnabled()) logger.info("order updated");
//	}

	@Override
	@Transactional
	public void deleteOrder(Order order) {
		if(logger.isInfoEnabled()) logger.info("delete order");
		orderRepository.delete(order);
		if(logger.isInfoEnabled()) logger.info("order updated");
	}

	@Override
	public Shipper loadShipper(Integer id) {
		return null;
	}

	@Override
	public Shipper findShipper(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteShipper(Shipper shipper) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShipper(Shipper shipper) {
		// TODO Auto-generated method stub

	}

}
