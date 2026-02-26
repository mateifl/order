package ro.zizicu.mservice.order.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.data.CustomerFinderRepository;

import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;

@Service
public class CustomerServiceImpl 
	   extends CrudServiceImpl<Customer, String> 
	   implements CustomerService
{
	@Autowired
	private CustomerFinderRepository finderRepository;

	@Override
	protected Customer transform(Customer e) {
		return e;
	}

	public CustomerServiceImpl(CrudRepository<Customer, String> repository) {
		super(repository);
	}

	@Override
	public List<Customer> findWithCriteria(String customerCode, String region, String city, String country) {
		return finderRepository.find(customerCode, region, city, country);
	}

	@Override
	public Customer update(Customer customer) {
		return null;
	}

}
