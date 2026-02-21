package ro.zizicu.mservice.order.data.impl;

import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.finder.PredicateBuilder;
import ro.zizicu.nwbase.entity.IdentityOwner;

public interface CriteriaFinder<T extends IdentityOwner<?>> {
	void prepare();
	void setupWhereClause(List<QueryParameter<?>> parameters, PredicateBuilder predicateBuilder);
	List<T> find();
}

@Slf4j
abstract class AbstractCriteriaFinder<T extends IdentityOwner<?>> implements CriteriaFinder<T> {
	
	private EntityManager em;
	private Class<T> clazz;
	protected CriteriaQuery<T> query;
	protected Root<T> root;
	protected CriteriaBuilder criteriaBuilder;

	public void prepare() {
		log.info("using finder for class " + this.clazz.toString());
		
		if(em == null)
		{
			log.error("Entity manager is null in finder");
			return;
		}
		
		criteriaBuilder = em.getCriteriaBuilder();
		query = criteriaBuilder.createQuery(clazz);
		root = query.from(clazz);
		query.select(root);
	}

	public void setupWhereClause(List<QueryParameter<?>> parameters, PredicateBuilder predicateBuilder) {
		
		
	}

	public List<T> find() {
		// TODO Auto-generated method stub
		return null;
	}
	
}