package ro.zizicu.mservice.order.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.exceptions.OrderNotFoundException;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.CustomerService;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.mservice.order.services.ShipperService;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

	private static Logger logger = LoggerFactory.getLogger(OrderController.class);
	private OrderService orderService;
	private EmployeeService employeeService;
	private CustomerService customerService;
	private ShipperService shipperService;
	
	@Autowired
	public OrderController(OrderService orderService, 
							EmployeeService employeeService,
							CustomerService customerService,
							ShipperService shipperService) {
		this.orderService = orderService;
		this.employeeService = employeeService;
		this.customerService = customerService;
		this.shipperService = shipperService;
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getOrder(@PathVariable String id) {
		if(logger.isInfoEnabled())  
			logger.info("Load order");
		try {
			Order order = orderService.loadOrder(Integer.valueOf(id));
			return ResponseEntity.ok(order);
		} catch (OrderNotFoundException e) {
			logger.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public ResponseEntity<String> createOrder(@RequestBody OrderCreateWrapper orderCreateWrapper) {
		if(logger.isInfoEnabled()) { 
			logger.info("Creating order for customer: "	+ orderCreateWrapper.getCustomerCode());
			logger.info("shipper id: "	+ orderCreateWrapper.getShipperId());
		}
		try {
			Order order = orderCreateWrapper.getOrder();
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();
			Integer employeeId = orderCreateWrapper.getEmployeeId();
			Employee employee = employeeService.load(employeeId);
			String customerCode = orderCreateWrapper.getCustomerCode();
			List<Customer> customers = customerService.findWithCriteria(customerCode, null, null, null);
			if(customers.isEmpty())
			{
				customerService.create(orderCreateWrapper.getCustomer());
			}
			Shipper shipper = shipperService.load(orderCreateWrapper.getShipperId());
			orderService.createOrder(order, products, employee, customers.get(0), shipper);
			return ResponseEntity.ok(order.getId().toString());
		} catch (ProductNotFoundException e) {
			logger.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public ResponseEntity<String> updateOrder(@RequestBody OrderCreateWrapper orderCreateWrapper) {
		if(logger.isInfoEnabled()) 
			logger.info("Update order with id: " + orderCreateWrapper.getOrder().getId());
		try {
			Order order = orderCreateWrapper.getOrder();
			List<ProductValueObject> products = orderCreateWrapper.getProductIds();
			orderService.updateOrder(order, products);
			return ResponseEntity.ok(order.getId().toString());
		} catch (ProductNotFoundException e) {
			logger.error("", e);
			return ResponseEntity.notFound().build();
		}
	}
	
}

class OrderCreateWrapper {
	
	private Order order;
	private List<ProductValueObject> productIds; 
	private String customerCode; 
	private Integer employeeId; 
	private Integer shipperId;
	private Customer customer;
	
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	} 
	
}