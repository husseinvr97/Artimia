// Service Layer
package Artimia.com.services;

import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.entities.Products;
import Artimia.com.enums.Style;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService 
{

    private final ProductsRepository productsRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public ResponseEntity<ProductGet> createProduct(ProductCreate productCreate,MultipartFile image) throws IOException
    {
        Products product = new Products();
        product.setProductName(productCreate.productName());
        product.setPrice(productCreate.Price());
        product.setDescription(productCreate.description());
        product.setStyle(productCreate.style());
        if (image != null && !image.isEmpty()) 
        {
            System.out.println("Working fine");
            String filename = fileStorageService.storeFile(image);
            product.setImageUrl("/uploads/" + filename);
        }
        
        Products savedProduct = productsRepository.save(product);
        return new ResponseEntity<>(convertToProductGet(savedProduct),HttpStatus.CREATED);
    }

    public Page<ProductGet> getAllProducts(Pageable pageable) 
    {
        return productsRepository.findAll(pageable).map(this::convertToProductGet);
    }

    public ProductGet getProductById(Long id) 
    {
        return productsRepository.findById(id).map(this::convertToProductGet).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Page<ProductGet> searchProducts(Style style, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) 
    {
        return productsRepository.searchProducts(style,minPrice != null ? minPrice : BigDecimal.ZERO,maxPrice != null ? maxPrice : BigDecimal.valueOf(1000000),pageable)
        .map(this::convertToProductGet);
    }

    public Page<Object[]> getProductSummaries(Pageable pageable) {
        return productsRepository.findProductSummaries(pageable);
    }

    public List<ProductGet> getTopProducts() 
    {
        return productsRepository.findTop10ByOrderByTimesBoughtDesc().stream().map(this::convertToProductGet).toList();
    }

    @Transactional
    public void incrementTimesBought(Long productId) 
    {
        productsRepository.findById(productId).ifPresent(product -> 
        {
            product.setTimesBought(product.getTimesBought() + 1);
            productsRepository.save(product);
        });
    }

    @Transactional
    public void deleteProduct(Long id)
    {
        productsRepository.deleteById(id);
    }

    private ProductGet convertToProductGet(Products product) 
    {
        return new ProductGet
        (
            product.getProductName(),
            product.getProductId(),
            product.getPrice(),
            product.getDescription(),
            product.getStyle(),
            product.getImageUrl(),
            product.getTimesBought()
        );
    }
}