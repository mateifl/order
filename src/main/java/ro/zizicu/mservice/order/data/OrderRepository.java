package ro.zizicu.mservice.order.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>, OrderFinderRepository {


    @Query(value = "select ", nativeQuery = true)
    Long getTransactionId();
}
