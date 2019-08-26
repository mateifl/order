package ro.zizicu.mservice.order.services;

import java.io.Serializable;
import java.util.List;

/** TODO Is this needed ? */

public interface CrudService<Entity, ID extends Serializable> {
	Entity load(ID id);
	List<Entity> loadAll();
	Entity create(Entity entity);
	Entity update(Entity entity);
	void delete(Entity entity);
	void deleteById(ID id);
}
