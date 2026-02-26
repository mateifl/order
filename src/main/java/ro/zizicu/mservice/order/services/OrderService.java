package ro.zizicu.mservice.order.services;

import java.util.Date;
import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.nwbase.service.CrudService;

public interface OrderService extends CrudService<Order, Integer> {
	
    Order createOrder(Order order, 
    				List<ProductValueObject> productIds,
    				Integer employeeId,
    				String customerId,
					Integer shipperId) throws ProductNotFoundException;
    Order update(Order order, 
    				 List<ProductValueObject> productIds) throws ProductNotFoundException;
	void cancelOrder(Order order);
//	List<Order> findOrders(Customer customer, Date start, Date end, String countryToShip, Employee createdBy);
}
