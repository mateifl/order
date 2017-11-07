package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ro.zizicu.mservice.order.data.impl.PredicateBuilder;
import ro.zizicu.mservice.order.data.impl.QueryParameter;

/** This class will find entities according to the Predicate builder passed */

public class EntityCriteriaFinder<T> extends CriteriaFinder<T> {

	private Class<T> clazz;
	private CriteriaQuery<T> query;
	private Root<T> root;
	private List<QueryParameter> parameters;
	
	public EntityCriteriaFinder(EntityManager em, Class<T> clazz) {
		super(em);
		this.clazz = clazz;
		query = criteriaBuilder.createQuery(clazz);
		root = query.from(clazz);
		query.select(root);
	}

	public void setParameters(	List<QueryParameter> parameters, 
								PredicateBuilder predicateBuilder) {
		this.parameters = parameters;
//		Predicate predicate = predicateBuilder.create(parameters, root, criteriaBuilder);
//		query.where(predicate);
	}
	
	@Override
	public void setup() {
		
	}

	@Override
	public void execute() {
		TypedQuery<T> typedQuery = em.createQuery(query);
		for(QueryParameter p1 : parameters) {
			if(p1.value == null) continue;
			typedQuery.setParameter(p1.name, p1.value);
		}
		results = typedQuery.getResultList();
	}


	
	
}
