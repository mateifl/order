package ro.zizicu.mservice.order.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.OrderDetail;
import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.mservice.order.entities.Shipper;

public class TestJSONSerialization {

	public static void main(String[] args) {
		try {
			Order order = new Order();
			order.setId(1);
			order.setShipAddress("test address");
			order.setShipCountry("Romania");
			order.setShipName("test ship name");
			Customer c = new Customer();
			c.setCity("Bucuresti");
			c.setRegion("test region");
			order.setCustomer(c);
			Employee e = new Employee();
			e.setCity("test city");
			e.setAddress("Adress employee");
			order.setEmployee(e);
			Shipper s = new Shipper();
			s.setCompanyName("test shipper");
			order.setShipper(s);
			OrderDetail od = new OrderDetail();
			od.setDiscount(100.);
			od.setQuantity(12);
			od.setProduct(new Product());
			OrderDetail od1 = new OrderDetail();
			od1.setDiscount(101.);
			od1.setQuantity(13);
			od.setProduct(new Product());
			List<OrderDetail> ods = new ArrayList<>();
			ods.add(od);
			ods.add(od1);
			order.setOrderDetails(ods);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new PrintWriter(System.out), order);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}