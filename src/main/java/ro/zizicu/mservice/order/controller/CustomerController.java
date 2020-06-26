package ro.zizicu.mservice.order.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;


@RestController
@RequestMapping(value = "/customers")
public class CustomerController  {

	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	private CustomerService customerService; 
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public ResponseEntity<?> loadCustomers() {
		List<Customer> customers = customerService.loadAll();
		return ResponseEntity.ok(customers);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> load(@PathVariable String id) {
		Customer customer = customerService.load(id);
		return ResponseEntity.ok(customer);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		Customer c = customerService.create(customer);
		return ResponseEntity.ok(c);
	}
	
	@RequestMapping(value = "/find", method=RequestMethod.GET)
	public ResponseEntity<?> find(@RequestParam(value="code", defaultValue="") String code,
								  @RequestParam(value="city", defaultValue="") String city,
								  @RequestParam(value="country", defaultValue="") String country) {
		
		List<Customer> result = customerService.findWithCriteria(code.isEmpty() ? null : code, 
																null, 
																city.isEmpty() ? null : city, 
																country.isEmpty() ? null : country);
		logger.debug("Found " + result.size());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Customer customer) {
		if(customer.getId() == null) {
			return ResponseEntity.badRequest().build();
		}
		Customer c = customerService.update(customer);
		return ResponseEntity.ok(c);
	}
//	
//	@RequestMapping(value = "/", method=RequestMethod.DELETE)
//	public void delete(@PathVariable String id) {
//		basicController.delete(id);
//	}
}
