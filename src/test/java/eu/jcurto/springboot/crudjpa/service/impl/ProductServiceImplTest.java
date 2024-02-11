package eu.jcurto.springboot.crudjpa.service.impl;

import eu.jcurto.springboot.crudjpa.dto.ProductDTO;
import eu.jcurto.springboot.crudjpa.entity.Product;
import eu.jcurto.springboot.crudjpa.repository.ProductRepository;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Test
    void findAllTest() {
        final ProductRepository productRepository = mock(ProductRepository.class);
        final ProductService productService = new ProductServiceImpl(productRepository);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1L, "Product 1", "Description 1", 100));
        expectedProducts.add(new Product(2L, "Product 2", "Description 2", 200));
        expectedProducts.add(new Product(3L, "Product 3", "Description 3", 300));

        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<ProductDTO> actualProducts = productService.findAll();

        assertEquals(expectedProducts.size(), actualProducts.size());

        for (int i = 0; i < expectedProducts.size(); i++) {
            ProductDTO actualProductDTO = actualProducts.get(i);
            Product expectedProduct = expectedProducts.get(i);

            assertEquals(expectedProduct.getId(), actualProductDTO.getId());
            assertEquals(expectedProduct.getName(), actualProductDTO.getName());
            assertEquals(expectedProduct.getDescription(), actualProductDTO.getDescription());
            assertEquals(expectedProduct.getPrice(), actualProductDTO.getPrice());
        }
    }

    @Test
    void findByIdTest() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        Product expectedProduct = new Product(1L, "Product 1", "Description 1", 100);
        ProductDTO expectedProductDTO = convertToProductDTO(expectedProduct);

        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        Optional<ProductDTO> actualProductOptional = productService.findById(1L);

        assertTrue(actualProductOptional.isPresent());

        ProductDTO actualProductDTO = actualProductOptional.get();

        assertEquals(expectedProductDTO.getId(), actualProductDTO.getId());
        assertEquals(expectedProductDTO.getName(), actualProductDTO.getName());
        assertEquals(expectedProductDTO.getDescription(), actualProductDTO.getDescription());
        assertEquals(expectedProductDTO.getPrice(), actualProductDTO.getPrice());
    }

    @Test
    void saveTest() {
        ProductDTO expectedProductDTO = new ProductDTO();
        expectedProductDTO.setId(1L);
        expectedProductDTO.setName("Product 1");
        expectedProductDTO.setDescription("Description 1");
        expectedProductDTO.setPrice(100);

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product 1");
        expectedProduct.setDescription("Description 1");
        expectedProduct.setPrice(100);

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        // Act
        ProductDTO actualProductDTO = productService.save(expectedProductDTO);

        // Assert
        assertNotNull(actualProductDTO);
        assertEquals(expectedProductDTO.getId(), actualProductDTO.getId());
        assertEquals(expectedProductDTO.getName(), actualProductDTO.getName());
        assertEquals(expectedProductDTO.getDescription(), actualProductDTO.getDescription());
        assertEquals(expectedProductDTO.getPrice(), actualProductDTO.getPrice());
    }

    @Test
    void deleteByIdTest() {
        Product product = new Product(1L, "Product 1", "Description 1", 100);
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductDTO> deletedProductDTO = productService.deleteById(1L);

        assertTrue(deletedProductDTO.isPresent());
        assertEquals(product.getId(), deletedProductDTO.get().getId());
        assertEquals(product.getName(), deletedProductDTO.get().getName());
        assertEquals(product.getDescription(), deletedProductDTO.get().getDescription());
        assertEquals(product.getPrice(), deletedProductDTO.get().getPrice());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }
}