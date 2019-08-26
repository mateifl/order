package ro.zizicu.mservice.order.service;


import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.mservice.order.services.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmployeeService employeeService;
	
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
			order.setShippedDate(today);
			order.setShipPostalCode("12212212");
			order.setShipRegion("test region");
			List<ProductValueObject> products = new ArrayList<>();
			products.add(new ProductValueObject(4, 1.0, 1.0, 1));
			products.add(new ProductValueObject(5, 1.5, 3.0, 2));
			Shipper s = new Shipper();
			s.setCompanyName("Shipper 1");
			s.setPhone("12221212");
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
			orderService.createOrder(order, products, e, c, s);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
