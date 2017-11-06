package ro.zizicu.mservice.order.data;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.data.impl.CriteriaFinder;
import ro.zizicu.mservice.order.data.impl.QueryParameter;
import ro.zizicu.mservice.order.entities.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaFinderTests {

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void findCustomers() {
		CriteriaFinder<Customer> finder = new CriteriaFinder<>(em, Customer.class);
		List<QueryParameter> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", String.class, "AN%"));
		parameters.add(new QueryParameter<>("country", String.class, "Mex%"));
		List<Customer> l =  finder.find(parameters);
		assertNotNull("Null result in find customers test ", l);
		assertTrue(!l.isEmpty());
	}
	
	@Test
	public void findCustomers1parameters() {
		CriteriaFinder<Customer> finder = new CriteriaFinder<>(em, Customer.class);
		List<QueryParameter> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", String.class, "AN%"));
		List<Customer> l =  finder.find(parameters);
		assertNotNull("Null result in find customers test ", l);
		assertTrue(!l.isEmpty());
	}
}
