package ro.zizicu.mservice.order.data.integration;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.data.finder.EntityCriteriaFinder;
import ro.zizicu.mservice.order.data.finder.Finder;
import ro.zizicu.mservice.order.data.finder.StringLikePredicateBuilder;
import ro.zizicu.mservice.order.data.impl.QueryParameter;
import ro.zizicu.mservice.order.entities.Customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriteriaFinderTest extends BaseIntegrationTest {

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testLikeFinder() {
		StringLikePredicateBuilder predicateBuilder = new StringLikePredicateBuilder();
		Finder<Customer> finder = new EntityCriteriaFinder<>(em, Customer.class, predicateBuilder);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", "AN%"));
		finder.setup(parameters);
		finder.execute();
		List<Customer> l = finder.getResults();
		assertNotNull("Null result in find customers test ", l);
		assertFalse(l.isEmpty());
	}
	
	@Test
	public void testLikeFinder2() {
		StringLikePredicateBuilder predicateBuilder = new StringLikePredicateBuilder();
		Finder<Customer> finder = new EntityCriteriaFinder<>(em, Customer.class, predicateBuilder);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", "AN%"));
		parameters.add(new QueryParameter<>("companyName", "An%"));
		finder.setup(parameters);
		finder.execute();
		List<Customer> l = finder.getResults();
		assertNotNull("Null result in find customers test ", l);
		assertFalse(l.isEmpty());
	}
}
