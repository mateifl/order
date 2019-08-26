package ro.zizicu.mservice.order.controller;

import java.io.Serializable;
import java.util.List;

import ro.zizicu.mservice.order.entities.IdentityOwner;
import ro.zizicu.mservice.order.services.CrudService;

class BasicOperationsController<Entity extends IdentityOwner<ID>, 
								ID extends Serializable> {
		
	BasicOperationsController(CrudService<Entity, ID> service) {
		this.service = service;
	}
	
	final CrudService<Entity, ID> service;

	Entity load(ID id) {
		return service.load(id);
	}
	
	List<Entity> loadAll() {
		return service.loadAll();
	}
	
	
}

