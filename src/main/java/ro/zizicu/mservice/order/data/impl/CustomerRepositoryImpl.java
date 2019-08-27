package ro.zizicu.mservice.order.data.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.zizicu.mservice.order.data.CustomerFinderRepository;
import ro.zizicu.mservice.order.entities.Customer;

public class CustomerRepositoryImpl implements CustomerFinderRepository {

	private static Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Customer> find(String customerCode, String region, String city, String country) {
		logger.info("find customer");
		CriteriaFinderImpl<Customer> finder = new CriteriaFinderImpl<>(em, Customer.class);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		if(customerCode != null)
			parameters.add(new QueryParameter<String>("id", customerCode));
		if(region != null)
			parameters.add(new QueryParameter<String>("region", region));
		if(city != null)
			parameters.add(new QueryParameter<String>("city", city));
		if(country != null)
			parameters.add(new QueryParameter<String>("country", country));
		return finder.find(parameters);
	}
	
}
