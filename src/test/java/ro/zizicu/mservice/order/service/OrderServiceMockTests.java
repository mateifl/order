package ro.zizicu.mservice.order.service;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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
		when(repository.findOne(1)).thenReturn(order);
		Order o = service.load(1);
		
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
}
