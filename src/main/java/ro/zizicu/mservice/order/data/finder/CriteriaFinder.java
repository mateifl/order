package ro.zizicu.mservice.order.data.finder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;



public abstract class CriteriaFinder<T> implements Finder<T> {
	
	protected EntityManager em;
	protected CriteriaBuilder criteriaBuilder;
	protected List<T> results;

	public CriteriaFinder(EntityManager em) {
		this.em = em;
	}
	
	
	/** TODO why does this have an empty implementation */
	protected void processResults() {
		
	}

	@Override
	public List<T> getResults() {
		processResults();
		return results;
	}
}

