// Service Layer
package Artimia.com.services;

import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.entities.Products;
import Artimia.com.enums.Style;
import Artimia.com.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService 
{

    private final ProductsRepository productsRepository;

    @Transactional
    public ProductGet createProduct(ProductCreate productCreate) {
        Products product = new Products();
        product.setProductName(productCreate.productName());
        product.setPrice(productCreate.Price());
        product.setDescription(productCreate.description());
        product.setStyle(productCreate.style());
        product.setImageUrl(productCreate.imageUrl());
        
        Products savedProduct = productsRepository.save(product);
        return convertToProductGet(savedProduct);
    }

    public Page<ProductGet> getAllProducts(Pageable pageable) {
        return productsRepository.findAll(pageable)
                .map(this::convertToProductGet);
    }

    public Optional<ProductGet> getProductById(Long id) {
        return productsRepository.findById(id)
                .map(this::convertToProductGet);
    }

    public Page<ProductGet> searchProducts(Style style, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productsRepository.searchProducts(
                        style,
                        minPrice != null ? minPrice : BigDecimal.ZERO,
                        maxPrice != null ? maxPrice : BigDecimal.valueOf(1000000),
                        pageable
                )
                .map(this::convertToProductGet);
    }

    public Page<Object[]> getProductSummaries(Pageable pageable) {
        return productsRepository.findProductSummaries(pageable);
    }

    public List<ProductGet> getTopProducts() {
        return productsRepository.findTop10ByOrderByTimesBoughtDesc()
                .stream()
                .map(this::convertToProductGet)
                .toList();
    }

    @Transactional
    public void incrementTimesBought(Long productId) {
        productsRepository.findById(productId).ifPresent(product -> {
            product.setTimesBought(product.getTimesBought() + 1);
            productsRepository.save(product);
        });
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