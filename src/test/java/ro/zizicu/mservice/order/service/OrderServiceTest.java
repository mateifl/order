package ro.zizicu.mservice.order.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
@Slf4j
public class OrderServiceTest extends BaseIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Test
	public void testFind() {
		List<Order> orders = orderService.findOrders("VINET", null, null, null, null);
		assertNotNull(orders);
		assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testLoad() {
		try {
			Order order = orderService.load(10248);
			assertNotNull(order);
			assertEquals(10248, (int) order.getId());
		} catch (EntityNotFoundException e) {
			log.error("", e);
			fail();
		}
		
	}
	
	@Test
	public void testLoadOrderNotExists() {
		try {
			Order order = orderService.load(1);
			fail();
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testSaveOrderFullGraph() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date today = calendar.getTime();
			Order order = new Order();
			order.setFreight(10.0);
			order.setOrderDate(today);
			order.setRequiredDate(today);
			order.setShipAddress("test 12345");
			order.setShipCity("test city");
			order.setShipCountry("test country");
			order.setShipName("ship name");
			order.setShipPostalCode("12212212");
			order.setShipRegion("test region");
			List<ProductValueObject> products = new ArrayList<>();
			ProductValueObject product1 = new ProductValueObject();
			product1.setId(6);
			product1.setUnitsOnOrder(2);
			products.add(product1);

			order = orderService.createOrder(order, products, 1, "VINET", 2);
			assertNotNull(order.getId());
			// clean up 
			orderService.delete(order.getId());
		} catch (ProductNotFoundException e) {
			log.error("error creating order", e);
			fail();
		} 
	}
	
}
