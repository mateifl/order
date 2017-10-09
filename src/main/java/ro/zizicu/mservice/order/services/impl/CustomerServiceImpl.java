package ro.zizicu.mservice.order.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.data.CustomerRepository;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.services.CustomerService;

@Service
public class CustomerServiceImpl 
	   extends SimpleServiceImpl<CustomerRepository, Customer, String> 
	   implements CustomerService
{

	@Override
	public List<Customer> findWithCriteria() {
//		getRepository().f
		return null;
	}
	
}
