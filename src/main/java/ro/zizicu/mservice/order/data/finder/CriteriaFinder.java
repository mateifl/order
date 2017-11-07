package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class CriteriaFinder<T> implements Finder<T> {
	
	protected EntityManager em;
//	protected CriteriaQuery<T> query;
//	protected Root<T> root;
	protected CriteriaBuilder criteriaBuilder;
	protected List<T> results;

	public CriteriaFinder(EntityManager em) {
		this.em = em;
//		this.criteriaBuilder = em.getCriteriaBuilder();
	}
	
	protected void processResults() {
		
	}

	@Override
	public List<T> getResults() {
		processResults();
		return results;
	}
}

