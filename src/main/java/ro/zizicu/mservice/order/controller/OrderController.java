package ro.zizicu.mservice.order.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderController {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);

	private final OrderService orderService;
	private final EmployeeService employeeService;
	private final CustomerService customerService;
	private BasicOperationsController<Order, Integer> basicOperationsController;

	public OrderController(	OrderService orderService, 
							EmployeeService employeeService, 
							CustomerService customerService) {
		this.orderService = orderService;
		this.employeeService = employeeService;
		this.customerService = customerService;
		basicOperationsController = new BasicOperationsController<>();
		basicOperationsController.setCrudService(orderService);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@Valid @RequestBody OrderCreateWrapper orderCreateWrapper) {
		if(logger.isInfoEnabled()) { 
			logger.info("create order for customer: "	+ orderCreateWrapper.getCustomerCode());
			logger.info("shipper id: "	+ orderCreateWrapper.getShipperId());
		}
		try {
			logger.debug("create order: " + orderCreateWrapper);
			Order order = orderCreateWrapper.getOrder();
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();

			Integer employeeId = orderCreateWrapper.getEmployeeId();
			Employee employee = employeeService.load(employeeId);
			String customerCode = orderCreateWrapper.getCustomerCode();
			List<Customer> customers = customerService.findWithCriteria(customerCode, null, null, null);

			order = orderService.createOrder(order, products, employee, customers.get(0), orderCreateWrapper.getShipperId());
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("Location", "orders/" + order.getId());
			return ResponseEntity.ok().headers(responseHeaders).body(order);
		} catch (ProductNotFoundException e) {
			logger.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/")
	public ResponseEntity<String> updateOrder(@RequestBody OrderCreateWrapper orderCreateWrapper) {
		if(logger.isInfoEnabled()) 
			logger.info("Update order with id: " + orderCreateWrapper.getOrder().getId());
		try {
			Order order = orderCreateWrapper.getOrder();
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();
			order = orderService.update(order, products);
			return ResponseEntity.ok(order.getId().toString());
		} catch (ProductNotFoundException e) {
			logger.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> load(@PathVariable Integer id) {
		return basicOperationsController.load(id);
	}
	
}

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
	
	public Integer getShipperId() {
		return shipperId;
	}

	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<ProductValueObject> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<ProductValueObject> productIds) {
		this.productIds = productIds;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "OrderCreateWrapper [order=" + order + 
				", productIds=" + productIds + 
				", customerCode=" + customerCode + 
				", employeeId=" + employeeId + 
				", shipperId=" + shipperId + "]";
	} 
	
	
}