package ro.zizicu.mservice.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.controller.exceptions.ResourceNotFoundException;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;


@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService; 
	
	@RequestMapping(value = "/customers/", method=RequestMethod.GET)
	public ResponseEntity<?> loadCustomers() {
		List<Customer> customers = customerService.loadAll();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> load(@PathVariable String id) {
		Customer customer = customerService.load(id);
		if(customer == null) 
			throw new ResourceNotFoundException("Customer not found, id: " + id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		customerService.save(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/customers/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Customer customer) {
		if(customer.getCustomerId() == null) {
			
		}
		customerService.save(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	public void delete(Customer customer) {
		
	}
}
