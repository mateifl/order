package ro.zizicu.mservice.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.services.OrderService;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

	private OrderService orderService;
	private BasicOperationsController<OrderService, Order, Integer> basicController;	
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
		basicController = new BasicOperationsController<OrderService, Order, Integer>(this.orderService);
	}
	
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getOrder(@PathVariable Integer id) {
		return basicController.load(id);
	}
	
//	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
//	public Order updateOrder(@PathVariable Integer id) {
//		Order order = orderService.load(id);
//		return order;
//	}
	
	
	@RequestMapping(value = "/{id}", method=RequestMethod.POST)
	public void createOrder(@RequestBody OrderCreateWrapper orderCreateWrapper) {
		Order order = orderCreateWrapper.getOrder();
		List<ProductValueObject> products = orderCreateWrapper.getProductIds();
		Integer employeeId = orderCreateWrapper.getEmployeeId();
		String customerCode = orderCreateWrapper.getCustomerCode();
		Integer shipperId = orderCreateWrapper.getShipperId();
		orderService.createOrder(order, products, employeeId, customerCode, shipperId);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public void deleteOrder(@PathVariable Integer id) {
		orderService.delete(id);
		
	}
}

class OrderCreateWrapper {
	
	private Order order;
	private List<ProductValueObject> productIds; 
	private String customerCode; 
	private Integer employeeId; 
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
	
}