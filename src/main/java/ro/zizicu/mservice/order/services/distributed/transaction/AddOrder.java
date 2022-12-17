package ro.zizicu.mservice.order.services.distributed.transaction;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.mservice.order.data.OrderRepository;
import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.nwbase.transaction.support.TransactionStep;


@RequiredArgsConstructor
@Setter
@Slf4j
@Component
@RequestScope
public class AddOrder implements TransactionStep {

	private final OrderRepository orderRepository;
	private Order order;
	
	@Override
	public void execute() {
		log.debug("save order");
		orderRepository.save(order);
	}

}
