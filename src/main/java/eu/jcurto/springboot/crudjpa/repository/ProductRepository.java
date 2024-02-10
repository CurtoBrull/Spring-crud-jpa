package eu.jcurto.springboot.crudjpa.repository;

import eu.jcurto.springboot.crudjpa.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
