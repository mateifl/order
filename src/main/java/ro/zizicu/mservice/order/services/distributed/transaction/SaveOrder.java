package ro.zizicu.mservice.order.services.distributed.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.mservice.order.entities.Customer;
import ro.zizicu.mservice.order.entities.Employee;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.nwbase.transaction.support.AbstractTransactionStep;


@RequiredArgsConstructor
@Slf4j
public class SaveOrder extends AbstractTransactionStep {

	private final Order order;
	private final Integer employeeId;
	private final String customerCode;
	@Override
	public void execute() {
		lastStep = Boolean.TRUE;
		setServiceName("order");
		log.debug("save order");
		Customer c = entityManager.find(Customer.class, customerCode);
		Employee e = entityManager.find(Employee.class, employeeId);
		order.setCustomer(c);
		order.setEmployee(e);
		entityManager.persist(order);
		log.debug("order saved");
	}
}
