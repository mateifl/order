package ro.zizicu.mservice.order.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class SimpleServiceImpl<Repository extends CrudRepository<Entity, ID>, 
							   Entity, 
							   ID extends Serializable> 
{
	@Autowired
	private Repository repository;
	
	/** Do I need this? */
	protected Repository getRepository() {
		return repository;
	}
	
	public void delete(Entity entity) {
		repository.delete(entity);
	}
	
	public void save(Entity entity) {
		repository.save(entity);
	}
	
	public List<Entity> loadAll() {
		List<Entity> entities = new ArrayList<>();
		
		for(Entity e : repository.findAll())
			entities.add(e);
		
		return entities;
	}
	
	public Entity load(ID id) {
		return repository.findOne(id);
	}
}
