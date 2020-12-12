package ro.zizicu.mservice.order.services.impl;

import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;


@Service
public class EmployeeServiceImpl 
			 extends CrudServiceImpl<Employee, Integer> 
			 implements EmployeeService {



}
