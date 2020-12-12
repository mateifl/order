package ro.zizicu.mservice.order.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;
import ro.zizicu.nwbase.controller.BasicOperationsController;


@RestController
@RequestMapping(value = "/customers")
public class CustomerController extends BasicOperationsController<Customer, String> {

	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private CustomerService customerService; 
	
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		return create(customer);
	}
	
	@GetMapping(value = "/find")
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
}
