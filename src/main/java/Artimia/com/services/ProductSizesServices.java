// ProductSizesService.java
package Artimia.com.services;

import Artimia.com.dtos.productsizesDTOs.ProductSizesCreate;
import Artimia.com.dtos.productsizesDTOs.ProductSizesGet;
import Artimia.com.entities.Products;
import Artimia.com.entities.ProductSizes;
import Artimia.com.exceptions.DuplicateProductSizeException;
import Artimia.com.exceptions.ProductNotFoundException;
import Artimia.com.repositories.ProductSizesRepository;
import Artimia.com.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSizesServices 
{

    private final ProductSizesRepository productSizesRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public ProductSizesGet createProductSize(ProductSizesCreate dto) {
        Products product = productsRepository.findById(dto.productId())
            .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + dto.productId()));

        if (productSizesRepository.existsByProductAndSize(product, dto.size())) {
            throw new DuplicateProductSizeException("Size " + dto.size() + " already exists for product ID " + dto.productId());
        }

        ProductSizes productSize = new ProductSizes();
        productSize.setProduct(product);
        productSize.setSize(dto.size());
        productSize.setLength(dto.length());
        productSize.setWidth(dto.width());
        productSize.setQuantity(dto.quantity());
        productSize.setAdditionalPrice(dto.additionalPrice());

        ProductSizes savedSize = productSizesRepository.save(productSize);
        return convertToGetDTO(savedSize);
    }

    private ProductSizesGet convertToGetDTO(ProductSizes productSize) {
        return new ProductSizesGet(
            productSize.getSizeId(),
            productSize.getProduct().getProductId(),
            productSize.getSize(),
            productSize.getLength(),
            productSize.getWidth(),
            productSize.getQuantity(),
            productSize.getAdditionalPrice()
        );
    }
}