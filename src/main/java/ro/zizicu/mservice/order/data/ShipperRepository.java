package ro.zizicu.mservice.order.data;

import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.Shipper;

public interface ShipperRepository extends CrudRepository<Shipper, Integer> {

}
