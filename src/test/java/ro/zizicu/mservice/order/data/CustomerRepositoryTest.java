package ro.zizicu.mservice.order.data;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.mservice.order.entities.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private CustomerFinderRepository finderRepository;	
	
	@Test
	public void testLoad() {
		Customer c = repository.findById("ANATR").get();
		assertNotNull(c);
		assertTrue(c.getId().equalsIgnoreCase("ANATR"));
	}
	
	@Test
	public void testSave() {
		Customer c = new Customer(); 
		c.setId("ANAZZ");
		c.setAddress("test address customer");
		c.setCity("test city");
		c.setCompanyName("test company");
		c.setContactName("test contact name");
		c.setContactTitle("test contact title");
		c.setCountry("test country");
		c.setPhone("0101010101");
		c.setPostalCode("097882");
		c.setRegion("regioan1");
		c.setFax("12121");
		repository.save(c);
		assertNotNull(c);
		repository.deleteById("ANAZZ");
	}
	
	@Test
	public void testFind() {
		
		List<Customer> customers = finderRepository.find("ANATR", null, null, null);
		assertNotNull(customers);
		assertEquals(1, customers.size());
		customers = finderRepository.find(null, null, null, "Germany");
		assertEquals(11, customers.size());
		customers = finderRepository.find("AN%", null, null, null);
		assertEquals(2, customers.size());
		customers = finderRepository.find("A%", null, null, "Germany");
		assertEquals(1, customers.size());
	}
}
