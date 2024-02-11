package eu.jcurto.springboot.crudjpa.service;

import eu.jcurto.springboot.crudjpa.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    
    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(Long id);

    ProductDTO save(ProductDTO product);

    Optional<ProductDTO> deleteById(Long id);

}
