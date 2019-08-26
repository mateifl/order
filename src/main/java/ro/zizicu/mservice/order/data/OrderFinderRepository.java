package ro.zizicu.mservice.order.data;

import java.util.Date;
import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;

public interface OrderFinderRepository {
	List<Order> findOrders(Customer customer, Date startDate, Date endDate, Employee employee);
}
