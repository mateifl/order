package ro.zizicu.mservice.order.services.impl;

import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.data.EmployeeRepository;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;

@Service
public class EmployeeServiceImpl 
			 extends SimpleServiceImpl<EmployeeRepository, Employee, Integer> 
			 implements EmployeeService {


}
