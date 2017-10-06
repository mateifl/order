package ro.zizicu.mservice.order.data;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {}
