package ro.zizicu.mservice.order.services;

import java.util.Date;
import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;

public interface OrderService {
	
    Order createOrder(Order order, 
    				List<ProductValueObject> productIds, 
    				Employee employee, 
    				Customer customer,
					Shipper shipper) throws ProductNotFoundException;
    void updateOrder(Order order, 
    				 List<ProductValueObject> productIds) throws ProductNotFoundException;
	void cancelOrder(Order order);
	Order loadOrder(Integer id) throws OrderNotFoundException;
	List<Order> findOrders(Customer customer);
	List<Order> findOrders(Date start, Date end);
	List<Order> findOrders(String countryToShip);
	List<Order> findOrders(Employee createdBy);
}
