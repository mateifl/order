package ro.zizicu.mservice.order.data.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.finder.PredicateBuilder;
import ro.zizicu.mservice.order.entities.IdentityOwner;

public interface CriteriaFinder<T extends IdentityOwner> {

	void prepare();
	
	void setupWhereClause(List<QueryParameter> parameters, PredicateBuilder predicateBuilder);
	
	List<T> find();
	
}

abstract class AbstractCriteriaFinder<T>  {
	
	private EntityManager em;
	private Class<T> clazz;
	protected CriteriaQuery<T> query;
	protected Root<T> root;
	protected CriteriaBuilder criteriaBuilder;
	private static Logger logger = LoggerFactory.getLogger(CriteriaFinder.class);

	public void prepare() {
		logger.info("using finder for class " + this.clazz.toString());
		
		if(em == null)
		{
			logger.error("Entity manager is null in finder");
			return;
		}
		
		criteriaBuilder = em.getCriteriaBuilder();
		query = criteriaBuilder.createQuery(clazz);
		root = query.from(clazz);
		query.select(root);
	}

	public void setupWhereClause(List<QueryParameter> parameters, PredicateBuilder predicateBuilder) {
		
		
	}

	public List<T> find() {
		// TODO Auto-generated method stub
		return null;
	}
	
}