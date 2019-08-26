package ro.zizicu.mservice.order.services;

import java.util.List;

import ro.zizicu.mservice.order.entities.Shipper;

public interface ShipperService extends CrudService<Shipper, Integer>  {

	List<Shipper> findByName(String name);
}
