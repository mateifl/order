package ro.zizicu.mservice.order.data.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.entities.IdentityOwner;

public class CriteriaFinderImpl<T extends IdentityOwner> {

	protected EntityManager em;
	protected Class<T> clazz;
	protected CriteriaQuery<T> query;
	protected Root<T> root;
	protected CriteriaBuilder criteriaBuilder;
	private static Logger logger = LoggerFactory.getLogger(CriteriaFinderImpl.class);
	
	public CriteriaFinderImpl(EntityManager em,  Class<T> clazz) {
		this.clazz = clazz;
		this.em = em;
	}
	
	public  List<T> find(List<QueryParameter<?>> parameters) {
		logger.info("using finder for class " + this.clazz.toString());
		
		if(em == null)
		{
			logger.error("Entity manager is null in finder");
			return null;
		}
		
		if(parameters == null || parameters.isEmpty()) {
			logger.warn("No paramers, returning null");
			return null;
		}
		
		criteriaBuilder = em.getCriteriaBuilder();
		query = criteriaBuilder.createQuery(clazz);
		root = query.from(clazz);
		query.select(root);
		return setupAndExecuteQuery(parameters);
	}
	
	@SuppressWarnings({ "rawtypes"})
	protected List<T> setupAndExecuteQuery(List<QueryParameter<?>> parameters) {
		Predicate predicate = criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
		for(int i = 0; i < parameters.size(); i++) {
			QueryParameter p = parameters.get(i);
			if(p.value == null)	continue;
			if(logger.isDebugEnabled()) logger.debug(p.toString());
			Predicate pred = criteriaBuilder.like(root.get(p.name), (String)p.value);
			predicate = criteriaBuilder.and(predicate, pred);
		}
		query.where(predicate);
		TypedQuery<T> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}
}
