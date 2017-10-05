package ro.zizicu.mservice.order.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {
	List<OrderDetail> findByOrder(Order order);
}
