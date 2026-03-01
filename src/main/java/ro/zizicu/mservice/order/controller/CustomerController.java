package ro.zizicu.mservice.order.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;

import java.net.URI;
import java.util.List;


@RestController
@Slf4j
@RequestMapping(value = "/customers")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
 		return ResponseEntity.ok(customerService.load(id));
	}

	@PostMapping
	public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
		log.debug("create customer");
		Customer c = customerService.create(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(c.getId())
				.toUri();
		return ResponseEntity.created(location).body(c);
	}


	@GetMapping("/find")
	public ResponseEntity<List<Customer>> find(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String country) {

		
		List<Customer> result = customerService.findWithCriteria(code,
																region,
																city,
																country);
        log.debug("Found {}", result.size());
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
