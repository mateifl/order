package ro.zizicu.mservice.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.services.OrderService;

@RestController
@RequestMapping(value = "orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/order/{id}")
	public Order getOrder(@PathVariable Integer id) {
		Order order = orderService.loadOrder(id);
		return order;
	}
	
}
