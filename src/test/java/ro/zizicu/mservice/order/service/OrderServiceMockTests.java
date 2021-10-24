package ro.zizicu.mservice.order.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.services.impl.OrderServiceImpl;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;

public class OrderServiceMockTests {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceMockTests.class);
	@Mock
	private OrderRepository repository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private EmployeeRepository employeeRepository; 
	@InjectMocks
	private OrderServiceImpl service;
	
	@Before 
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new OrderServiceImpl(null, repository, customerRepository);
		when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee()));
	}
	
	@Test
	public void testLoad() {
		try {
			logger.info("load order");
			Order order = new Order();
			order.setId(1);
			Optional<Order> o = Optional.of(order);
			when(repository.findById(1)).thenReturn(o);
			Order o1 = service.load(10248);

			assertEquals(1, (int) o1.getId());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test 
	public void testSave() {
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
			Employee e = employeeRepository.findById(1).get();
			Customer c = new Customer();
			service.createOrder(order, products, e, c, 2);
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			fail();
		}
		
	}


}
