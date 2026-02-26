package ro.zizicu.mservice.order.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.exceptions.ProductNotFoundException;
import ro.zizicu.mservice.order.restclient.RestClientImpl;
import ro.zizicu.mservice.order.services.impl.OrderServiceImpl;
import ro.zizicu.nwbase.exceptions.EntityNotFoundException;

@SpringBootTest
@Slf4j
public class OrderServiceMockTests {

	@Mock
	private OrderRepository repository;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private CustomerRepository customerRepository;

	private OrderServiceImpl service;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new OrderServiceImpl(repository, employeeRepository, customerRepository);
		when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee()));
	}
	
	@Test
	public void testLoad() {
		try {
			log.info("load order");
			Order order = new Order();
			order.setId(1);
			Optional<Order> o = Optional.of(order);
			when(repository.findById(10248)).thenReturn(o);
			Order o1 = service.load(10248);

			assertEquals(1, (int) o1.getId());
		} catch (EntityNotFoundException e) {
			log.error("", e);
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
			service.createOrder(order, products, 1, "sss", 2);
		} catch (ProductNotFoundException e) {
			log.error("", e);
			fail();
		}
	}
}
