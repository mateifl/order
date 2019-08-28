package ro.zizicu.mservice.order.data;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.zizicu.mservice.order.entities.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaQueryTest {

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
			cal.set(1997, 0, 1);
			
			TypedQuery<Order> query = em.createQuery(cq);
			query.setParameter(startDateParameter, cal.getTime());
			List<Order> orders = query.getResultList();
			
			assertTrue(!orders.isEmpty());
			assertTrue(orders.size() == 676);
			
			ParameterExpression<Date> endDateParameter = cb.parameter(Date.class);
			Predicate between = cb.between(order.get("orderDate"), startDateParameter, endDateParameter);
			cq.select(order).where(between);
			
			query = em.createQuery(cq);
			query.setParameter(startDateParameter, cal.getTime());
			cal.set(1997, 3, 1);
			query.setParameter(endDateParameter, cal.getTime());
			orders = query.getResultList();
			
			assertTrue(!orders.isEmpty());
			assertTrue(orders.size() == 91);
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
			cal.set(1997, 0, 1);
			
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
