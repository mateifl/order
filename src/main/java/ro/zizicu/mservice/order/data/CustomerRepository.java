package ro.zizicu.mservice.order.data;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Customer;

/** */
public interface CustomerRepository extends CrudRepository<Customer, String>, CustomerFinderRepository {
		
//	List<Customer> findWithCriteria(String name, String country, String city);

}
