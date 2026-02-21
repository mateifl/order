package ro.zizicu.mservice.order.data.finder;

import java.util.List;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ro.zizicu.mservice.order.data.impl.QueryParameter;

/** 
 * This class will create a predicate by adding "like" operators for 
 * every member of the parameter list. It implies that every member is a 
 * String. 
 * @author mflorescu
 */

public class StringLikePredicateBuilder implements PredicateBuilder {

	@Override
	public Predicate create(List<QueryParameter<?>> parameters,
							Root<?> root,
							CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
		for(int i = 0; i < parameters.size(); i++) {
			QueryParameter<?> p = parameters.get(i);
			if(p.value == null)	continue;
			Predicate pred = criteriaBuilder.like(root.get(p.name), (String)p.value);
			predicate = criteriaBuilder.and(predicate, pred);
		}

		return predicate;
	}
	
}