package ro.zizicu.mservice.order.data.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
		List<QueryParameter> parameters = new ArrayList<>();
		parameters.add(new QueryParameter<>("id", String.class, customerCode));
		parameters.add(new QueryParameter<>("region", String.class, region));
		parameters.add(new QueryParameter<>("city", String.class, city));
		parameters.add(new QueryParameter<>("country", String.class, country));
		return finder.find(parameters);
	}
	
}
