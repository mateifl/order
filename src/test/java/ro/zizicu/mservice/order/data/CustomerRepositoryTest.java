package ro.zizicu.mservice.order.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.entities.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private CustomerFinderRepository finderRepository;	
	
	@Test
	public void testLoad() {
		Customer c = repository.findById("ANATR").get();
		assertTrue(c != null);
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
		assertTrue(c != null);
		repository.deleteById("ANAZZ");
	}
	
	@Test
	public void testFind() {
		
		List<Customer> customers = finderRepository.find("ANATR", null, null, null);
		assertNotNull("find by", customers);
		assertTrue(customers.size() == 1);
		customers = finderRepository.find(null, null, null, "Germany");
		assertTrue(customers.size() == 11);
		customers = finderRepository.find("AN%", null, null, null);
		assertTrue(customers.size() == 2);
		customers = finderRepository.find("A%", null, null, "Germany");
		assertTrue(customers.size() == 1);
	}
}
