package ro.zizicu.mservice.order.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.Shipper;


public interface OrderRepository extends CrudRepository<Order, Integer> {
	@Query("select o from Order o where o.customer = ?")
	List<Order> findByCustomer(Customer customer);
	@Query("select o from Order o where o.shipper = ?")
	List<Order> findByShipper(Shipper shipper);

	/* TODO do this methods belong here? or do i need a separate repository 
	 * for the order detail?
	 */
//	void addOrderDetail(OrderDetail orderDetail);
//	void deleteOrderDetail(OrderDetail orderDetail);
	
}
