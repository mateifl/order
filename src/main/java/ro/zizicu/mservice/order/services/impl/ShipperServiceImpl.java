package ro.zizicu.mservice.order.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.zizicu.mservice.order.data.ShipperRepository;
import ro.zizicu.mservice.order.entities.Shipper;
import ro.zizicu.mservice.order.services.ShipperService;

@Service
public class ShipperServiceImpl 
		extends CrudServiceImpl<ShipperRepository, Shipper, Integer> 
		implements ShipperService 
{

	@Override
	public List<Shipper> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


}
