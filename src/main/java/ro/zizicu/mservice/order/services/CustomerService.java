package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;

public interface CustomerService extends CrudService<Customer, String>{
	Customer create(Customer customer);
	List<Customer> findWithCriteria(String customerCode, String region, String city, String country);
	Customer load(String id);
	
}
