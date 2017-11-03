package ro.zizicu.mservice.order.data.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.entities.IdentityOwner;

public abstract class Finder<T extends IdentityOwner<ID>, ID> {

	private final String query;
	private Class<T> clazz;
	private static Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);
	
	public Finder(String query, Class<T> clazz) {
		this.query = query;
		this.clazz = clazz;
	}
	
	@PersistenceContext
	private EntityManager em;
	
	public List<T> find(List<QueryParameter> parameters) {
		logger.info("find");
		TypedQuery<T> typedQuery = em.createQuery(query, clazz);
		mapParameters(typedQuery, parameters);
		return typedQuery.getResultList();
	}
	
	protected abstract void mapParameters(TypedQuery<T> typedQuery, List<QueryParameter> parameters);

	
}


