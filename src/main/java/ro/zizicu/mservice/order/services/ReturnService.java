package ro.zizicu.mservice.order.services;

import ro.zizicu.mservice.order.entities.Product;
import ro.zizicu.mservice.order.entities.Shipper;

public interface ReturnService {

	public void receiveReturn(Product product, Shipper shipper);
	
}
