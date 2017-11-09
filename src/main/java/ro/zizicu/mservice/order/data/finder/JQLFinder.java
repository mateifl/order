/**
 * 
 */
package ro.zizicu.mservice.order.data.finder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.impl.QueryParameter;

public abstract class JQLFinder<T> implements Finder<T> {

	private static Logger logger = LoggerFactory.getLogger(JQLFinder.class);
	private EntityManager em;
	private Query query;
	private List<T> results;
	private ParameterMapper mapper;
	private List<QueryParameter> parameters;
	protected abstract String getStatement();
//	protected abstract String getStatement();
	
	public JQLFinder(EntityManager em, ParameterMapper mapper) {
		this.em = em;
		this.mapper = mapper;
	}
	
	public void setup() {
		if(logger.isDebugEnabled()) logger.debug("Setup JQL Finder");
		query = em.createQuery(getStatement());
		if(logger.isDebugEnabled()) logger.debug("Mapping parameters ...");
		mapper.mapParameters(query, parameters);
	}

	@Override
	public void execute() {
		results = query.getResultList();
	}

	@Override
	public List<T> getResults() {
		return results;
	}

}
