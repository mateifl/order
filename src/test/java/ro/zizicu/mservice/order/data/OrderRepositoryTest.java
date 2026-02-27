package ro.zizicu.mservice.order.data;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.mservice.order.entities.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository; 
	@Autowired
	private EmployeeRepository employeeRepository; 
	@Autowired
	private CustomerRepository customerRepository; 	
	@Autowired
	private CustomerFinderRepository customerFinderRepository; 	

	@Test
	public void testOrderSaveNoDetails() {
		Order order = createOrder();
		Employee e = employeeRepository.findAll().iterator().next();
		order.setEmployee(e);
		Customer c = createCustomer("Test2");
		order.setCustomer(c);
		order.setShipperId(1);
		order = orderRepository.save(order);
		c = order.getCustomer();
		assertNotNull(order.getId());
		orderRepository.delete(order);
		customerRepository.delete(c);
	}

	@Test
	public void testOrderSave() {
		Order order = createOrder();
		Employee e = employeeRepository.findAll().iterator().next();
		order.setEmployee(e);
		Customer c = createCustomer("Test2");
		order.setCustomer(c);
		order.setShipperId(1);

		OrderDetail od = new OrderDetail();
		od.setOrder(order);
		od.setUnitPrice(1.1);
		od.setQuantity(1);
		od.setDiscount(0.0);
		OrderDetailId orderDetailId = new OrderDetailId();
		orderDetailId.setProductId(1);
		od.setId(orderDetailId);
		order.getOrderDetails().add(od);

		Order savedOrder = orderRepository.save(order);
		c = order.getCustomer();
		assertNotNull(order.getId());
		orderRepository.delete(savedOrder);
		customerRepository.delete(c);
	}


	@Test
	public void testFindByCustomer() {
		log.info("Test find orders");
		List<Customer> customers = customerFinderRepository.find("ALFKI", null, null, null);
		assertEquals(1, customers.size());
		List<Order> orders = orderRepository.findOrders(customers.getFirst(), null, null, null);
		assertEquals(6, orders.size());
		Calendar cal = Calendar.getInstance();
		cal.set(1997, Calendar.JANUARY, 1);
		Date startDate = cal.getTime();
		cal.set(1997, Calendar.JULY, 1);
		Date endDate = cal.getTime();
		log.info("Test find order with start date");
		orders = orderRepository.findOrders(null, startDate, null, null);
        assertFalse(orders.isEmpty());
		log.info("Test find order with end date");
		orders = orderRepository.findOrders(null, null, endDate, null);
        assertFalse(orders.isEmpty());
		log.info("Test find order dates between");
		orders = orderRepository.findOrders(null, startDate, endDate, null);
        assertFalse(orders.isEmpty());
	}
	
	@Test
	public void testFindByEmployee() {
		log.info("Test find employee");
		Employee e = employeeRepository.findById(5).orElse(null);
		List<Order> orders = orderRepository.findOrders(null, null, null, e);
		assertNotNull(orders);
		assertEquals(42, orders.size());
	}
	
	private Order createOrder() {
		Date today = new Date();
		Order order = new Order();
		order.setFreight(10.0);
		order.setOrderDate(today);
		order.setRequiredDate(today);
		order.setShipAddress("test 12345");
		order.setShipCity("test city");
		order.setShipCountry("test country");
		order.setShipName("ship name");
		order.setShippedDate(today);
		order.setShipPostalCode("12212212");
		order.setShipRegion("test region");
		return order;
	}

	private Order createOrderNoDates() {
		Order order = new Order();
		order.setFreight(10.0);
		order.setShipAddress("test 12345");
		order.setShipCity("test city");
		order.setShipCountry("test country");
		order.setShipName("ship name");
		order.setShipPostalCode("12212212");
		order.setShipRegion("test region");
		return order;
	}

	private Customer createCustomer(String id) {
		Customer c = new Customer();
		c.setId(id);
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
		return c;
	}
}
