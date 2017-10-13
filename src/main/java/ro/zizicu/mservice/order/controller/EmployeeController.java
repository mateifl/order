package ro.zizicu.mservice.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;

@RestController
@RequestMapping(value = "orders")
public class EmployeeController {

	private EmployeeService employeeService; 
	private BasicOperationsController<EmployeeService, Employee, Integer> basicController;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
		basicController = 
			new BasicOperationsController<EmployeeService, Employee, Integer>(this.employeeService);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public ResponseEntity<?> loadEmployees() {
		return basicController.loadAll();
	}

	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> loadEmployee(Integer id) {
		return basicController.load(id);
	}
	
}
