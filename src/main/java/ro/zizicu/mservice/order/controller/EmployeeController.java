package ro.zizicu.mservice.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;

import ro.zizicu.nwbase.controller.CrudOperationsController;

@RestController
@RequestMapping(value = "employees")
public class EmployeeController extends CrudOperationsController<Employee, Integer>{
	public EmployeeController(EmployeeService service) {
		super(service);
	}

	@Override
	protected String getLocation() {
		return "";
	}
}
