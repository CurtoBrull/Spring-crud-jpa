package eu.jcurto.springboot.crudjpa.service.impl;

import eu.jcurto.springboot.crudjpa.entity.Product;
import eu.jcurto.springboot.crudjpa.repository.ProductRepository;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Devuelve todos los productos
     *
     * @return List de Product
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    /**
     * Busca un producto por su id
     *
     * @param id Id del producto
     * @return Optional de Product
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Guarda un producto
     *
     * @param product Producto
     * @return Product
     */
    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Elimina un producto por su id
     *
     * @param id Id del producto
     * @return Optional de Product
     */
    @Override
    @Transactional
    public Optional<Product> deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
        return product;
    }
}
