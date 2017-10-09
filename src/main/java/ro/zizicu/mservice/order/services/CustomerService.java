package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;

public interface CustomerService extends CrudService<Customer, String> {
	List<Customer> findWithCriteria();
}
