package ro.zizicu.mservice.order.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.services.EmployeeService;
import ro.zizicu.nwbase.service.impl.CrudServiceImpl;


@Service
public class EmployeeServiceImpl 
			 extends CrudServiceImpl<Employee, Integer> 
			 implements EmployeeService {

	@Override
	protected Employee transform(Employee e) {
		Employee emp = new Employee();
		BeanUtils.copyProperties(e, emp, "reportsTo");
		return emp;
	}

	public EmployeeServiceImpl(CrudRepository<Employee, Integer> repository) {
		super(repository);
	}
}
