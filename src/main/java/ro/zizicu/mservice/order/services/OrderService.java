package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Order;
import ro.zizicu.mservice.order.entities.ProductValueObject;
import ro.zizicu.mservice.order.entities.Shipper;

public interface OrderService {

    void createOrder(Order order, List<ProductValueObject> productIds, Integer employeeId, String customerCode);
    void updateOrder(Order order, List<ProductValueObject> productIds);
	void deleteOrder(Order order);
	Order loadOrder(Integer id);
	Shipper loadShipper(Integer id);
	Shipper findShipper(String name);
	void deleteShipper(Shipper shipper);
	void updateShipper(Shipper shipper);
}
