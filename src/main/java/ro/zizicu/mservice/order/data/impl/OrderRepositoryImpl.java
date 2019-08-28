package ro.zizicu.mservice.order.data.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.OrderFinderRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;

public class OrderRepositoryImpl implements OrderFinderRepository {

	@Override
	public List<Order> findOrders(Customer customer, Date startDate, Date endDate, Employee employee) {
		if(logger.isDebugEnabled()) logger.debug("Find orders");
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> order = orderCriteriaQuery.from(Order.class);
		orderCriteriaQuery.select(order);
		
		List<Predicate> criteria = new ArrayList<>();
		if( customer != null && customer.getId() != null )
		{
			Join<Order, Customer> customerJoin = order.join("customer");
			ParameterExpression<String> p = criteriaBuilder.parameter(String.class, "customer");
			criteria.add(criteriaBuilder.equal(customerJoin.get("id"), p));
		}
		
		if(startDate != null && endDate != null) 
			criteria.add(criteriaBuilder.between(order.get("orderDate"), startDate, endDate));
		else if(startDate != null && endDate == null) 
			criteria.add(criteriaBuilder.greaterThanOrEqualTo(order.get("orderDate"), startDate));
		else if(startDate == null && endDate != null)
			criteria.add(criteriaBuilder.lessThanOrEqualTo(order.get("orderDate"), endDate));
		
		if( employee != null )
		{
			Join<Order, Employee> employeeJoin = order.join("employee");
			ParameterExpression<Integer> p = criteriaBuilder.parameter(Integer.class, "employee");
			criteria.add(criteriaBuilder.equal(employeeJoin.get("id"), p));
		}
		
		if (criteria.size() == 0) {
			throw new RuntimeException("no criteria");
		} else if (criteria.size() == 1) {
			orderCriteriaQuery.where(criteria.get(0));
		} else {
			orderCriteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[0])));
		}
		
		TypedQuery<Order> query = em.createQuery(orderCriteriaQuery);
		if(customer != null && customer.getId() != null)
			query.setParameter("customer", customer.getId());
		if(employee != null )
			query.setParameter("employee", employee.getId());
		return query.getResultList();
	}

	private static Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager em;

}
