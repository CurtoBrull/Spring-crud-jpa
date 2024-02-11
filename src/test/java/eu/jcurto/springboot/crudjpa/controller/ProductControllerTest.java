package eu.jcurto.springboot.crudjpa.controller;

import eu.jcurto.springboot.crudjpa.dto.ProductDTO;
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
    void listTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        List<ProductDTO> expectedProducts = new ArrayList<>();
        expectedProducts.add(ProductDTO.builder().id(1L).name("Product 1").description("Description 1").price(10).build());
        expectedProducts.add(ProductDTO.builder().id(2L).name("Product 2").description("Description 2").price(20).build());
        expectedProducts.add(ProductDTO.builder().id(3L).name("Product 3").description("Description 3").price(30).build());

        when(productService.findAll()).thenReturn(expectedProducts);

        List<ProductDTO> actualProducts = productController.list();

        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void productByIdTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        ProductDTO expectedProductDTO = ProductDTO.builder().id(1L).name("Product 1").description("Description 1").price(10).build();

        when(productService.findById(1L)).thenReturn(Optional.of(expectedProductDTO));

        ProductDTO actualProductDTO = productController.productById(1L).getBody();

        assertEquals(expectedProductDTO, actualProductDTO);
    }

    @Test
    void createProductTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(10)
                .build();

        when(productService.save(any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.createProduct(productDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(productDTO.getId(), response.getBody().getId());
    }

    @Test
    void updateProductTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        ProductDTO originalProduct = ProductDTO.builder()
                .id(1L)
                .name("Original Product")
                .description("Original Description")
                .price(10)
                .build();

        ProductDTO updatedProduct = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(20)
                .build();

        when(productService.findById(1L)).thenReturn(Optional.of(originalProduct));
        when(productService.save(any(ProductDTO.class))).thenReturn(updatedProduct);

        ResponseEntity<ProductDTO> response = productController.updateProduct(updatedProduct, 1L);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(updatedProduct.getId(), response.getBody().getId());
        assertEquals(updatedProduct.getName(), response.getBody().getName());
        assertEquals(updatedProduct.getDescription(), response.getBody().getDescription());
        assertEquals(updatedProduct.getPrice(), response.getBody().getPrice());
    }

    @Test
    void updateProductNotFoundTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        ProductDTO updatedProduct = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(20)
                .build();

        when(productService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ProductDTO> response = productController.updateProduct(updatedProduct, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteProductTest() {
        ProductService productService = mock(ProductService.class);
        ProductController productController = new ProductController(productService);

        ProductDTO productToDelete = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(10)
                .build();

        when(productService.deleteById(1L)).thenReturn(Optional.of(productToDelete));

        ResponseEntity<ProductDTO> response = productController.deleteProduct(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productToDelete, response.getBody());

        verify(productService).deleteById(1L);
    }
}