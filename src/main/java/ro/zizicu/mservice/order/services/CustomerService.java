package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.nwbase.service.CrudService;

public interface CustomerService extends CrudService<Customer, String>{

	List<Customer> findWithCriteria(String customerCode, String region, String city, String country);

    Customer update(Customer customer);
}
