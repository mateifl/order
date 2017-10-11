package ro.zizicu.mservice.order.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Customer;

/** */
public interface CustomerRepository extends CrudRepository<Customer, String> {
		
//	List<Customer> findWithCriteria(String name, String country, String city);

}
