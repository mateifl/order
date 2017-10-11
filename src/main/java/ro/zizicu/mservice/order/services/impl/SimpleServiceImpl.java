package ro.zizicu.mservice.order.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ro.zizicu.mservice.order.entities.IdentityOwner;

public class SimpleServiceImpl<Repository extends CrudRepository<Entity, ID>, 
							   Entity extends IdentityOwner<ID>, 
							   ID extends Serializable> 
{
	private static Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);
	@Autowired
	private Repository repository;
	
	/** Do I need this? */
	protected Repository getRepository() {
		return repository;
	}
	
	public void delete(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("delete: " + entity.getClass().getName() + " id " + entity.getId());
		repository.delete(entity);
	}
	
	public void save(Entity entity) {
		if(logger.isInfoEnabled()) logger.info("save: " + entity.getClass().getName() + " id " + entity.getId());
		repository.save(entity);
	}
	
	public List<Entity> loadAll() {
		if(logger.isInfoEnabled()) logger.info("load all entities");
		List<Entity> entities = new ArrayList<>();
		
		for(Entity e : repository.findAll())
			entities.add(e);
		
		return entities;
	}
	
	public Entity load(ID id) {
		if(logger.isInfoEnabled()) logger.info("load entity with id " + id);
		return repository.findOne(id);
	}

	public void delete(ID id) {
		if(logger.isInfoEnabled()) logger.info("delete entity with id " + id);
		repository.delete(id);
	}
}
