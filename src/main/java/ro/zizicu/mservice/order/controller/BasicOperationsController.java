package ro.zizicu.mservice.order.controller;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ro.zizicu.mservice.order.controller.exceptions.ResourceNotFoundException;
import ro.zizicu.mservice.order.entities.IdentityOwner;
import ro.zizicu.mservice.order.services.CrudService;

class BasicOperationsController<Service extends CrudService<Entity, ID>, Entity extends IdentityOwner<ID>, ID extends Serializable> {
		
	BasicOperationsController(Service service) {
		this.service = service;
	}
	
	private Service service;
	
	public ResponseEntity<?> load(ID id) {
		Entity entity = service.load(id);
		if(entity == null) 
			throw new ResourceNotFoundException("entity not found, id: " + id);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	public ResponseEntity<?> loadAll() {
		List<Entity> entities = service.loadAll();
		return new ResponseEntity<>(entities, HttpStatus.OK);
	}
	
	public ResponseEntity<?> save(Entity entity) {
		service.save(entity);
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(entity.getId())
						.toUri();
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);
	}
	
	public ResponseEntity<?> delete(ID id) {
		service.delete(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}

