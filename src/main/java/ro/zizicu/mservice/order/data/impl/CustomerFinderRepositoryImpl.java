package ro.zizicu.mservice.order.data.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ro.zizicu.mservice.order.data.CustomerFinderRepository;
import ro.zizicu.mservice.order.entities.Customer;

public class CustomerFinderRepositoryImpl implements CustomerFinderRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Customer> find(String customerCode, String region, String city, String country) {
		if((customerCode == null || customerCode.isEmpty()) &&
		   (region == null || region.isEmpty()) &&
		   (city == null || city.isEmpty()) &&
		   (country == null || country.isEmpty()))
		{
			return null;
		}
		StringBuilder sb = new StringBuilder("from Customer where ");
		boolean customerSet = false;
		boolean regionSet = false;
		boolean citySet = false;
		if( customerCode != null && !customerCode.isEmpty() ) {
			sb.append("customerid = '" + customerCode + "'");
			customerSet = true;
		}
		if( region != null && !region.isEmpty() )
		{
			if(customerSet)
				sb.append(" and ");
			sb.append("region = '" + region + "'");
			regionSet = true;
		}
		if( city != null && !city.isEmpty() )
		{
			if(customerSet || regionSet)
				sb.append(" and ");
			sb.append("city = '" + city + "'");
			citySet = true;
		}
		if( country != null && !country.isEmpty() ) {
			if(customerSet || regionSet || citySet)
				sb.append(" and ");
			sb.append(" country = '" + country + "'");
		}
		Query query = em.createQuery(sb.toString());
		List<Customer> customers = query.getResultList();
		return customers;
	}

	
	
}
