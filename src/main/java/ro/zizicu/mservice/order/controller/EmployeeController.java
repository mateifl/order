package ro.zizicu.mservice.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.nwbase.controller.BasicOperationsController;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController extends BasicOperationsController<Employee, Integer>{
	
	@PostMapping(value = "/")
	public ResponseEntity<?> create(@RequestBody Employee employee) {
		return create(employee);
	}
	
}
