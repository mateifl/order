package ro.zizicu.mservice.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;

@RestController
public class EmployeeController {

	private EmployeeService employeeService; 
	private BasicOperationsController<EmployeeService, Employee, Integer> basicController;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
		basicController = 
			new BasicOperationsController<EmployeeService, Employee, Integer>(this.employeeService);
	}
	
}
