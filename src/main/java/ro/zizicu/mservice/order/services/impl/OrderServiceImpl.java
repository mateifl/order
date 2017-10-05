package ro.zizicu.mservice.order.services.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.services.OrderService;


public class OrderServiceImpl implements OrderService {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private OrderRepository orderRepository;
	
	@Override
	public void createOrder(Order order, List<ProductValueObject> productIds, Integer employeeId, String customerCode) {
		if(logger.isInfoEnabled()) logger.info("create order");
		orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order created");
	}

	@Override
	@Transactional
	public void updateOrder(Order order, List<ProductValueObject> productIds) {
		if(logger.isInfoEnabled()) logger.info("update order");
		orderRepository.save(order);
		if(logger.isInfoEnabled()) logger.info("order updated");
	}

	@Override
	@Transactional
	public void deleteOrder(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public Order loadOrder(Integer id) {
		Order order = orderRepository.findOne(id);
		return order;
	}

	@Override
	public Shipper loadShipper(Integer id) {
		// TODO Auto-generated method stub
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
