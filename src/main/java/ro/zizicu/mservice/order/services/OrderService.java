package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;

public interface OrderService extends CrudService<Order, Integer>{

    void save(Order order, 
    				 List<ProductValueObject> productIds, 
    				 Integer employeeId, 
    				 String customerCode,
					 Integer shipperId);
//    void updateOrder(Order order, List<ProductValueObject> productIds);
	void deleteOrder(Order order);
	Shipper loadShipper(Integer id);
	Shipper findShipper(String name);
	void deleteShipper(Shipper shipper);
	void updateShipper(Shipper shipper);
}
