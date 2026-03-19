package ro.zizicu.mservice.order.data.impl;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import ro.zizicu.mservice.order.data.CustomerFinderRepository;
import ro.zizicu.mservice.order.entities.Customer;

@Slf4j
public class CustomerRepositoryImpl implements CustomerFinderRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Customer> find(String customerCode, String region, String city, String country) {
		log.info("find customer");
		CriteriaFinderImpl<Customer> finder = new CriteriaFinderImpl<>(em, Customer.class);
		List<QueryParameter<?>> parameters = new ArrayList<>();
		if(customerCode != null)
			parameters.add(new QueryParameter<>("id", customerCode));
		if(region != null)
			parameters.add(new QueryParameter<>("region", region));
		if(city != null)
			parameters.add(new QueryParameter<>("city", city));
		if(country != null)
			parameters.add(new QueryParameter<>("country", country));
		return finder.find(parameters);
	}
	
}
