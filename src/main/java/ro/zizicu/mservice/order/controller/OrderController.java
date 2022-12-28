package ro.zizicu.mservice.order.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.Data;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.CustomerService;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.nwbase.controller.BasicOperationsController;

@RestController
@RequestMapping(value = "orders")
@Slf4j
public class OrderController {

	private final OrderService orderService;
	private final EmployeeService employeeService;
	private final CustomerService customerService;
	private final BasicOperationsController<Order, Integer> basicOperationsController;

	public OrderController(	OrderService orderService, 
							EmployeeService employeeService, 
							CustomerService customerService) {
		this.orderService = orderService;
		this.employeeService = employeeService;
		this.customerService = customerService;
		basicOperationsController = new BasicOperationsController<>(orderService);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@Valid @RequestBody OrderCreateWrapper orderCreateWrapper) {
		if(log.isInfoEnabled()) {
			log.info("create order for customer {}", orderCreateWrapper.getCustomerCode());
			log.info("shipper id  {}", orderCreateWrapper.getShipperId());
		}
		try {
			log.debug("create order: " + orderCreateWrapper);
			Order order = orderCreateWrapper.getOrder();
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();
			Integer employeeId = orderCreateWrapper.getEmployeeId();
			Integer shipperId = orderCreateWrapper.getShipperId();
			String customerCode = orderCreateWrapper.getCustomerCode();
			order = orderService.createOrder(order, products, employeeId, customerCode, shipperId);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Location", "orders/" + order.getId());
			return ResponseEntity.ok().headers(responseHeaders).body(order);
		} catch (ProductNotFoundException e) {
			log.error("", e);
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<String> updateOrder(@RequestBody OrderCreateWrapper orderCreateWrapper, @PathVariable Integer id) {
		if(log.isInfoEnabled())
			log.info("Update order with id: {}", id);
		try {
			Order order = orderCreateWrapper.getOrder();
			order.setId(id);
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();
			order = orderService.updateOrder(order, products);
			return ResponseEntity.ok(order.getId().toString());
		} catch (ProductNotFoundException e) {
			log.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> load(@PathVariable Integer id) {
		return basicOperationsController.load(id);
	}
	
}

@Data
class OrderCreateWrapper {
	@NotNull
	private Order order;
	@Size(min=1)
	private List<ProductValueObject> productIds; 
	@NotBlank
	private String customerCode;
	@NotNull
	private Integer employeeId;
	@NotNull
	private Integer shipperId;
}