package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ro.zizicu.mservice.order.data.impl.QueryParameter;

public interface PredicateBuilder {
	Predicate create(List<QueryParameter> parameters, 
					 Root root, 
					 CriteriaBuilder criteriaBuilder);
}



