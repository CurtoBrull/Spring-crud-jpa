package eu.jcurto.springboot.crudjpa.controller;

import eu.jcurto.springboot.crudjpa.dto.ProductDTO;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public List<ProductDTO> list() {
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> productById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.findById(id);

        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        ProductDTO createdProduct = productService.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable Long id) {
        Optional<ProductDTO> originalProductOpt = productService.findById(id);

        if (originalProductOpt.isPresent()) {
            ProductDTO originalProduct = originalProductOpt.get();
            originalProduct.setName(product.getName());
            originalProduct.setPrice(product.getPrice());
            originalProduct.setDescription(product.getDescription());

            ProductDTO updatedProduct = productService.save(originalProduct);

            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        Optional<ProductDTO> deletedProduct = productService.deleteById(id);
        return deletedProduct.map(product -> ResponseEntity.status(HttpStatus.OK)
                .body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
