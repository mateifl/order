package ro.zizicu.mservice.order.services;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.Shipper;

public interface ExpeditionService {
	public void sendProductsToCustomer(Order order, Shipper shipper);
	
}
