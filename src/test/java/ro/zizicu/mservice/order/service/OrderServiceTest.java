package ro.zizicu.mservice.order.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.CustomerService;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.mservice.order.services.OrderService;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;


@SpringBootTest
@Slf4j
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@Test
	public void testFind() {
		Customer c = customerService.load("HANAR");
		List<Order> orders = orderService.findOrders(c, null, null, null, null);
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
			e.printStackTrace();
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
//			order.setShippedDate(today);
			order.setShipPostalCode("12212212");
			order.setShipRegion("test region");
			List<ProductValueObject> products = new ArrayList<>();
			products.add(new ProductValueObject(4, 1.0, 1.0, 1));
			products.add(new ProductValueObject(5, 1.5, 3.0, 2));

			Employee e = employeeService.load(5);
			Customer c = new Customer();
			c.setId("Test1");
			c.setCity("Brasov");
			c.setAddress("This is the test address");
			c.setContactName("Test Contact Name");
			c.setContactTitle("Mr");
			c.setCompanyName("Company name");
			c.setCountry("Romania");
			c.setPhone("12134234");
			c.setPostalCode("098828");
			c.setFax("23123212");
			c.setRegion("region");
			order = orderService.createOrder(order, products, e, c, 2);
			assertNotNull(order.getId());
			// clean up 
			orderService.delete(order);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			fail();
		} 
	}
	
}
