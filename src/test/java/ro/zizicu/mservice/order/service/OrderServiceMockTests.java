package ro.zizicu.mservice.order.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import ro.zizicu.mservice.order.data.ProductRepository;
import ro.zizicu.mservice.order.data.ShipperRepository;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.services.impl.OrderServiceImpl;
import ro.zizicu.mservice.order.services.impl.SimpleServiceImpl;

public class OrderServiceMockTests {

	private static Logger logger = LoggerFactory.getLogger(OrderServiceMockTests.class);
	@Mock
	private OrderRepository repository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private CustomerRepository customerRepository;
	@Mock
	private EmployeeRepository employeeRepository; 
	@Mock
	private ShipperRepository shipperRepository;
	@Mock
	private SimpleServiceImpl<ShipperRepository, Shipper, Integer> shipperService;
	@InjectMocks
	private OrderServiceImpl service;
	
	@Before 
	public void setUp() {
		service = new OrderServiceImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testLoad() {
		logger.info("load order");
		Order order = new Order();
		order.setId(1);
		when(repository.findById(1).get()).thenReturn(order);
		Order o = service.load(1);
		assertTrue(o.getId() == 1);
	}
	
	@Test
	public void testShipper() {
		logger.info("load shipper");
		Shipper shipper = new Shipper();
		shipper.setId(1);
		shipper.setCompanyName("test");
		when(shipperService.load(1)).thenReturn(shipper);
		Shipper s = service.loadShipper(1);
		assertTrue("not the same object", shipper.getCompanyName().equalsIgnoreCase(s.getCompanyName()));
	}
	
	@Test 
	public void testSave() {
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
		service.saveOrder(order, products, 1, "ANTON", 1);
		
	}


}
