package ro.zizicu.mservice.order.controller;

import java.net.URI;
import java.util.List;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.OrderService;

@RestController
@RequestMapping(value = "orders")
@Slf4j
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@Valid @RequestBody CreateOrderWrapper createOrderWrapper) {
		if(log.isInfoEnabled()) {
            log.info("create order for customer: {}", createOrderWrapper.getCustomerId());
            log.info("shipper id: {}", createOrderWrapper.getShipperId());
		}
		try {
            log.debug("create order: {}", createOrderWrapper);
			Order order = orderService.createOrder(createOrderWrapper.getOrder(),
											createOrderWrapper.getProductIds(),
											createOrderWrapper.getEmployeeId(),
											createOrderWrapper.getCustomerId(),
											createOrderWrapper.getShipperId());

			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(order.getId()).toUri();
			return ResponseEntity.created(location).body(order);
		} catch (ProductNotFoundException e) {
			log.error("", e);
			return ResponseEntity.notFound().build();
		}
	}

//	@PatchMapping(value = "/{id}")
//	public ResponseEntity<String> updateOrder(@RequestBody CreateOrderWrapper createOrderWrapper, @PathVariable Integer id) {
//		if(log.isInfoEnabled())
//			log.info("Update order with id: {}", id);
//		try {
//			Order order = createOrderWrapper.getOrder();
//			order.setId(id);
//			List<Integer> products = createOrderWrapper.getProductIds();
//			order = orderService.update(order, products);
//			return ResponseEntity.ok(order.getId().toString());
//		} catch (ProductNotFoundException e) {
//			log.error("", e);
//			return ResponseEntity.notFound().build();
//		}
//	}

}

@Getter
@Setter
class CreateOrderWrapper {
	@NotNull
	private Order order;
	@Size(min=1)
	private List<ProductValueObject> productIds;
	@NotBlank
	private String customerId;
	@NotNull
	private Integer employeeId;
	@NotNull
	private Integer shipperId;
}