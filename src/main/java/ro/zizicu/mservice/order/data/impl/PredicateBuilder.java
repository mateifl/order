package ro.zizicu.mservice.order.data.impl;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateBuilder {
	Predicate create(List<QueryParameter> parameters, Root root);
}
