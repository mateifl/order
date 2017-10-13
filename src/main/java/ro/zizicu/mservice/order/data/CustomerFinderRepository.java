package ro.zizicu.mservice.order.data;

import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;

public interface CustomerFinderRepository {

	List<Customer> find(String customerCode, String region, String city, String country);
	
}
