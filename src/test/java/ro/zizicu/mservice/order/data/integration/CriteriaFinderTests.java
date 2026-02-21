package ro.zizicu.mservice.order.data.integration;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.data.impl.CriteriaFinderImpl;
import ro.zizicu.mservice.order.data.impl.QueryParameter;
import ro.zizicu.mservice.order.entities.Customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriteriaFinderTests extends BaseIntegrationTest {

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void findCustomers() {
		CriteriaFinderImpl<Customer> finder = new CriteriaFinderImpl<>(em, Customer.class);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", "AN%"));
		parameters.add(new QueryParameter<>("country", "Mex%"));
		List<Customer> l =  finder.find(parameters);
		assertNotNull("Null result in find customers test ", l);
		assertTrue(!l.isEmpty());
	}
	
	@Test
	public void findCustomers1parameters() {
		CriteriaFinderImpl<Customer> finder = new CriteriaFinderImpl<>(em, Customer.class);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", "AN%"));
		List<Customer> l =  finder.find(parameters);
		assertNotNull("Null result in find customers test ", l);
        assertFalse(l.isEmpty());
	}
}
