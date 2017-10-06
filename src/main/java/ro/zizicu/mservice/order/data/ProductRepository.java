package ro.zizicu.mservice.order.data;

import org.springframework.data.repository.CrudRepository;
import ro.zizicu.mservice.order.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {}
