package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ro.zizicu.mservice.order.data.impl.QueryParameter;

/** This class will find entities according to the Predicate builder passed */

public class EntityCriteriaFinder<T> extends CriteriaFinder<T> {

	private Class<T> clazz;
	private CriteriaQuery<T> query;
	private Root<T> root;
	private PredicateBuilder predicateBuilder;
	
	public EntityCriteriaFinder(EntityManager em, 
								Class<T> clazz, 
								PredicateBuilder predicateBuilder) {
		super(em);
		this.clazz = clazz;
		this.predicateBuilder = predicateBuilder;
		criteriaBuilder = em.getCriteriaBuilder();
		query = criteriaBuilder.createQuery(clazz);
		root = query.from(clazz);
		query.select(root);
	}

	@Override
	public void setup(List<QueryParameter<?>> parameters) {
		Predicate predicate = predicateBuilder.create(parameters, root, criteriaBuilder);
		query.where(predicate);
	}

	@Override
	public void execute() {
		TypedQuery<T> typedQuery = em.createQuery(query);
		results = typedQuery.getResultList();
	}
}
