package ro.zizicu.mservice.order.controller;


import java.util.List;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping(value = "/customers")
public class CustomerController extends BasicOperationsController<Customer, String> {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super(customerService);
		this.customerService = customerService;
	}

	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody Customer customer) {
		Customer c = customerService.create(customer);
		return ResponseEntity.ok(c);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<?> find(@RequestParam(value="code", defaultValue="") String code,
								  @RequestParam(value="city", defaultValue="") String city,
								  @RequestParam(value="country", defaultValue="") String country) {
		
		List<Customer> result = customerService.findWithCriteria(code.isEmpty() ? null : code, 
																null, 
																city.isEmpty() ? null : city, 
																country.isEmpty() ? null : country);
		log.debug("Found " + result.size());
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
