package ro.zizicu.mservice.order.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.IdentityOwner;
import ro.zizicu.mservice.order.services.CrudService;

public class CrudServiceImpl<Repository extends CrudRepository<Entity, ID>, 
							   Entity extends IdentityOwner<ID>, 
							   ID extends Serializable> 
	implements CrudService<Entity, ID>

{
	private static Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);
	@Autowired
	protected Repository repository;

	@Override
	public void delete(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("delete: " + entity.getClass().getName() + " id " + entity.getId());
		repository.delete(entity);
	}
	
	@Override
	public Entity create(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("create: " + entity.getClass().getName() + " id " + entity.getId());
		return repository.save(entity);
	}
	
	@Override
	public List<Entity> loadAll() {
		if(logger.isInfoEnabled()) logger.info("load all entities");
		List<Entity> entities = new ArrayList<>();
		
		for(Entity e : repository.findAll())
			entities.add(e);
		
		return entities;
	}
	
	@Override
	public Entity load(ID id) {
		if(logger.isInfoEnabled()) logger.info("load entity with id " + id);
		return repository.findById(id).get();
	}

	@Override
	public void deleteById(ID id) {
		if(logger.isInfoEnabled()) logger.info("delete entity with id " + id);
		repository.deleteById(id);
	}

	@Override
	public Entity update(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("update: " + entity.getClass().getName() + " id " + entity.getId());
		return repository.save(entity);
	}
}
