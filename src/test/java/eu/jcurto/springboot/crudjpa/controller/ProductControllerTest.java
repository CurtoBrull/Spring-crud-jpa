package eu.jcurto.springboot.crudjpa.controller;

import eu.jcurto.springboot.crudjpa.entity.Product;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Test
    void list() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1L, "Product 1", "Description 1", 10));
        expectedProducts.add(new Product(2L, "Product 2", "Description 2", 20));
        expectedProducts.add(new Product(3L, "Product 3", "Description 3", 30));

        when(productService.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productController.list();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void productById() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        Product expectedProduct = new Product(1L, "Product 1", "Description 1", 10);

        when(productService.findById(1L)).thenReturn(Optional.of(expectedProduct));

        Product actualProduct = productController.productById(1L).getBody();

        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void createProduct() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(10);

        when(productService.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(product.getId(), response.getBody().getId());
    }

    @Test
    void updateProduct() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        Product originalProduct = new Product();
        originalProduct.setId(1L);
        originalProduct.setName("Original Product");
        originalProduct.setDescription("Original Description");
        originalProduct.setPrice(10);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(20);

        when(productService.findById(1L)).thenReturn(Optional.of(originalProduct));
        when(productService.save(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(updatedProduct, 1L);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(updatedProduct.getId(), response.getBody().getId());
        assertEquals(updatedProduct.getName(), response.getBody().getName());
        assertEquals(updatedProduct.getDescription(), response.getBody().getDescription());
        assertEquals(updatedProduct.getPrice(), response.getBody().getPrice());
    }

    @Test
    void updateProductNotFound() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(20);

        when(productService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.updateProduct(updatedProduct, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProduct() {
        ProductService productService = mock(ProductService.class);

        ProductController productController = new ProductController(productService);

        Product productToDelete = new Product();
        productToDelete.setId(1L);
        productToDelete.setName("Test Product");
        productToDelete.setDescription("Test Description");
        productToDelete.setPrice(10);

        when(productService.deleteById(1L)).thenReturn(Optional.of(productToDelete));

        ResponseEntity<Product> response = productController.deleteProduct(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productToDelete, response.getBody());

        verify(productService).deleteById(1L);
    }
}