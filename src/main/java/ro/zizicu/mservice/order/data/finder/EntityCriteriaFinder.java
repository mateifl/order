package ro.zizicu.mservice.order.data.finder;

import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ro.zizicu.mservice.order.data.impl.QueryParameter;

/** This class will find entities according to the Predicate builder passed */

public class EntityCriteriaFinder<T> extends CriteriaFinder<T> {

	private final CriteriaQuery<T> query;
	private final Root<T> root;
	private final PredicateBuilder predicateBuilder;
	
	public EntityCriteriaFinder(EntityManager em,
								Class<T> clazz,
								PredicateBuilder predicateBuilder) {
		super(em);
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
