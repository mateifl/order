package ro.zizicu.mservice.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;


@RestController
public class CustomerController {

	private CustomerService customerService; 
	private BasicOperationsController<CustomerService, Customer, String> basicController;

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
		basicController = 
			new BasicOperationsController<CustomerService, Customer, String>(this.customerService);
	}
	
	@RequestMapping(value = "/customers/", method=RequestMethod.GET)
	public ResponseEntity<?> loadCustomers() {
		return basicController.loadAll();
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> load(@PathVariable String id) {
		return basicController.load(id);
	}
	
	@RequestMapping(value = "/customers/", method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		return basicController.save(customer);
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Customer customer) {
		if(customer.getId() == null) {
			
		}
		return basicController.save(customer);
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		
	}
}
