package ro.zizicu.mservice.order.data.finder;

import java.util.List;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ro.zizicu.mservice.order.data.impl.QueryParameter;

public interface PredicateBuilder {
    Predicate create(List<QueryParameter<?>> parameters,
                     Root<?> root,
                     CriteriaBuilder criteriaBuilder);
}



