package eu.jcurto.springboot.crudjpa.service.impl;

import eu.jcurto.springboot.crudjpa.entity.Product;
import eu.jcurto.springboot.crudjpa.repository.ProductRepository;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Test
    void findAll() {
        final ProductRepository productRepository = mock(ProductRepository.class);
        final ProductService productService = new ProductServiceImpl(productRepository);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1L, "Product 1", "Description 1", 100));
        expectedProducts.add(new Product(2L, "Product 2", "Description 2", 200));
        expectedProducts.add(new Product(3L, "Product 3", "Description 3", 300));

        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.findAll();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void findById() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        Product expectedProduct = new Product(1L, "Product 1", "Description 1", 100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> actualProductOptional = productService.findById(1L);

        assertTrue(actualProductOptional.isPresent());

        Product actualProduct = actualProductOptional.get();

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void save() {

        final ProductRepository productRepository = mock(ProductRepository.class);
        final ProductService productService = new ProductServiceImpl(productRepository);

        Product expectedProduct = new Product(1L, "Product 1", "Description 1", 100);

        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);

        Product actualProduct = productService.save(expectedProduct);

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void deleteById() {
        Product expectedProduct = new Product(1L, "Product 1", "Description 1", 100);

        ProductRepository productRepository = mock(ProductRepository.class);

        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        ProductService productService = new ProductServiceImpl(productRepository);

        Optional<Product> actualProduct = productService.deleteById(1L);

        assertEquals(Optional.of(expectedProduct), actualProduct);
    }
}