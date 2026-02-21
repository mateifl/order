package ro.zizicu.mservice.order.data.finder;

import java.util.List;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ro.zizicu.mservice.order.data.impl.QueryParameter;

/** 
 * Builds predicates chained with "AND" logical operator 
 * and == relationship
 */

class EqualPredicateBuilder implements PredicateBuilder {

	@Override
	public Predicate create(List<QueryParameter<?>> parameters,
							Root<?> root,
							CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
		for(int i = 0; i < parameters.size(); i++) {
			QueryParameter<?> p = parameters.get(i);
			if(p.value == null)	continue;
			Predicate pred = criteriaBuilder.equal(root.get(p.name), p.value);
			predicate = criteriaBuilder.and(predicate, pred);
		}

		return predicate;
	}
	
}
