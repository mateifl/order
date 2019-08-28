package ro.zizicu.mservice.order.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.data.impl.OrderRepositoryImpl;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.Shipper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository; 
	@Autowired
	private EmployeeRepository employeeRepository; 
	@Autowired
	private ShipperRepository shipperRepository; 
	@Autowired
	private CustomerRepository customerRepository; 	
	
	private static Logger logger = LoggerFactory.getLogger(OrderRepositoryTest.class);
	
	@Test
	public void testOrderSave() {
		Order order = createOrder();

		Shipper s = new Shipper();
		s.setCompanyName("Shipper 1");
		s.setPhone("12221212");
		order.setShipper(s);
		
		Employee e = employeeRepository.findAll().iterator().next();
		order.setEmployee(e);
		
		Customer c = createCustomer("Test2");
		order.setCustomer(c);
		
		order = orderRepository.save(order);
		c = order.getCustomer();
		s = order.getShipper();
		assertNotNull(order.getId());
		assertNotNull(s.getId());
		orderRepository.delete(order);
		shipperRepository.delete(s);
		customerRepository.delete(c);
	}	

	@Test
	public void testFindByCustomer() {
		logger.info("Test find orders");
		List<Customer> customers = customerRepository.find("ALFAA", null, null, null);
		assertEquals(1, customers.size());
		List<Order> orders = orderRepository.findOrders(customers.get(0), null, null, null);
		assertEquals(2, orders.size());
		Calendar cal = Calendar.getInstance();
		cal.set(1997, 1, 1);
		Date startDate = cal.getTime();
		cal.set(1997, 5, 1);
		Date endDate = cal.getTime();
		logger.info("Test find order with start date");
		orders = orderRepository.findOrders(null, startDate, null, null);
		assertEquals(645, orders.size());
		logger.info("Test find order with end date");
		orders = orderRepository.findOrders(null, null, endDate, null);
		assertEquals(307, orders.size());
		logger.info("Test find order dates between");
		orders = orderRepository.findOrders(null, startDate, endDate, null);
		assertEquals(122, orders.size());
	}
	
	@Test
	public void testFindByEmployee() {
		logger.info("Test find orders");
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
