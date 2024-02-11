package eu.jcurto.springboot.crudjpa.service.impl;

import eu.jcurto.springboot.crudjpa.dto.ProductDTO;
import eu.jcurto.springboot.crudjpa.entity.Product;
import eu.jcurto.springboot.crudjpa.repository.ProductRepository;
import eu.jcurto.springboot.crudjpa.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Devuelve todos los productos
     *
     * @return List de ProductDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por su id
     *
     * @param id Id del producto
     * @return Optional de ProductDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Guarda un producto
     *
     * @param productDTO Producto
     * @return ProductDTO
     */
    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        return convertToDTO(productRepository.save(product));
    }

    /**
     * Elimina un producto por su id
     *
     * @param id Id del producto
     * @return Optional de ProductDTO
     */
    @Override
    @Transactional
    public Optional<ProductDTO> deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);

        return product.map(this::convertToDTO);
    }

    /**
     * Convierte un producto a un DTO
     *
     * @param product Producto
     * @return ProductDTO
     */
    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
