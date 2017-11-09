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

import ro.zizicu.mservice.order.data.finder.EntityCriteriaFinder;
import ro.zizicu.mservice.order.data.finder.Finder;
import ro.zizicu.mservice.order.data.finder.StringLikePredicateBuilder;
import ro.zizicu.mservice.order.data.impl.QueryParameter;
import ro.zizicu.mservice.order.entities.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaFinderTest {

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testLikeFinder() {
		StringLikePredicateBuilder predicateBuilder = new StringLikePredicateBuilder();
		Finder<Customer> finder = new EntityCriteriaFinder<>(em, Customer.class, predicateBuilder);
		List<QueryParameter> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", "AN%"));
		finder.setup(parameters);
		finder.execute();
		List<Customer> l = finder.getResults();
		assertNotNull("Null result in find customers test ", l);
		assertTrue(!l.isEmpty());
	}
	
}
