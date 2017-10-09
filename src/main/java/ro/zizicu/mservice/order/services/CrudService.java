package ro.zizicu.mservice.order.services;

import java.io.Serializable;
import java.util.List;

public interface CrudService<Entity, ID extends Serializable> {
	Entity load(ID id);
	List<Entity> loadAll();
	void save(Entity entity);
	void delete(Entity entity);
}
