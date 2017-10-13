package ro.zizicu.mservice.order.service;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.services.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void testSaveOrder() {
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
//		products.add(new ProductValueObject(1, 12, 0.5, 10.));
//		products.add(new ProductValueObject(2, 14, 1.5, 11.));
//		products.add(new ProductValueObject(3, 16, 2.5, 12.));
		orderService.saveOrder(order, products, 1, "ANTON", 1);
	}
	
}
