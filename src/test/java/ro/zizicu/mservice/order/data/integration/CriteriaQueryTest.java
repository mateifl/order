package ro.zizicu.mservice.order.data.integration;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import ro.zizicu.mservice.order.BaseIntegrationTest;
import ro.zizicu.mservice.order.entities.Order;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriteriaQueryTest extends BaseIntegrationTest {

	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void testSimple() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Order> cq = cb.createQuery(Order.class);
			Root<Order> order = cq.from(Order.class);

			ParameterExpression<Date> startDateParameter = cb.parameter(Date.class);
			cq.select(order).where(cb.greaterThanOrEqualTo(order.get("orderDate"), startDateParameter));
			
			Calendar cal = Calendar.getInstance();
			cal.set(1997, Calendar.JANUARY, 1);
			
			TypedQuery<Order> query = em.createQuery(cq);
			query.setParameter(startDateParameter, cal.getTime());
			List<Order> orders = query.getResultList();

			assertFalse("no orders found", orders.isEmpty());

			ParameterExpression<Date> endDateParameter = cb.parameter(Date.class);
			Predicate between = cb.between(order.get("orderDate"), startDateParameter, endDateParameter);
			cq.select(order).where(between);
			
			query = em.createQuery(cq);
			query.setParameter(startDateParameter, cal.getTime());
			cal.set(1997, Calendar.MARCH, 1);
			query.setParameter(endDateParameter, cal.getTime());
			orders = query.getResultList();

			assertFalse("no orders found", orders.isEmpty());

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAndWhereClause() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Order> cq = cb.createQuery(Order.class);
			// create the root. 
			Root<Order> order = cq.from(Order.class);

			// create the parameters and predicates
			ParameterExpression<Date> startDateParameter = cb.parameter(Date.class);
			
			Calendar cal = Calendar.getInstance();
			cal.set(1997, Calendar.JANUARY, 1);
			
			ParameterExpression<String> shipCountryParameter = cb.parameter(String.class);
			// create the predicates
			Predicate p = cb.greaterThanOrEqualTo(order.get("orderDate"), startDateParameter);
			Predicate p2 = cb.and(p, cb.equal(order.get("shipCountry"), shipCountryParameter));
			
			cq.select(order).where(p2);
			
			TypedQuery<Order> query = em.createQuery(cq);
			query.setParameter(startDateParameter, cal.getTime());
			query.setParameter(shipCountryParameter, "Canada");
			List<Order> orders = query.getResultList();
			
			assertTrue(!orders.isEmpty());
			assertTrue(orders.size() == 26);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
