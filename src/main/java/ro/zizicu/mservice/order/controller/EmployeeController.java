package ro.zizicu.mservice.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.nwbase.controller.BasicOperationsController;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController extends BasicOperationsController<Employee, Integer>{
	public EmployeeController(EmployeeService service) {
		super(service);
	}

}
