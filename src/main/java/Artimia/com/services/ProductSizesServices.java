package Artimia.com.services;

import Artimia.com.dtos.productsizesDTOs.ProductSizesCreate;
import Artimia.com.dtos.productsizesDTOs.ProductSizesGet;
import Artimia.com.dtos.productsizesDTOs.UpdateProductSizes;
import Artimia.com.entities.Products;
import Artimia.com.entities.ProductSizes;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.mapper.ProductSizesMapper;
import Artimia.com.repositories.ProductSizesRepository;
import Artimia.com.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSizesServices {

    private final ProductSizesRepository productSizesRepository;
    private final ProductsRepository productsRepository;

    @Transactional
    public HttpStatus createProductSize(ProductSizesCreate dto) {
        Products product = productsRepository.findByProductName(dto.productName())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name: " + dto.productName()));

        if (productSizesRepository.existsByProductAndSize(product, dto.size()))
            throw new DuplicateResourceException(
                    "Size " + dto.size() + " already exists for product name " + dto.productName());

        if (productSizesRepository.existsByProductAndLengthAndWidthAndSize(product, dto.length(), dto.width(),
                dto.size()))
            throw new DuplicateResourceException("length" + dto.length() + " already exists for width " + dto.width());

        ProductSizes productSize = new ProductSizes();
        productSize.setProduct(product);
        productSize.setSize(dto.size());
        productSize.setLength(dto.length());
        productSize.setWidth(dto.width());
        productSize.setQuantity(dto.quantity());
        productSize.setAdditionalPrice(dto.additionalPrice());

        productSizesRepository.save(productSize);
        return HttpStatus.CREATED;
    }

    public List<ProductSizesGet> getAllByProductId(Long Id) {
        List<ProductSizesGet> sizes = productSizesRepository.findAllByProductId(Id)
                .orElseThrow(() -> new ResourceNotFoundException("No Sizes Found")).stream()
                .map(ProductSizesMapper::convertToGetDTO).toList();
        return sizes;
    }

    public HttpStatus update(UpdateProductSizes updateProductSizes, Long Id) {
        if (!productSizesRepository.existsById(Id))
            throw new ResourceNotFoundException("Size is not found");
        productSizesRepository.update(Id, updateProductSizes.size(), updateProductSizes.length(),
                updateProductSizes.width(), updateProductSizes.quantity(), updateProductSizes.additionalPrice());
        return HttpStatus.OK;
    }

    public HttpStatus deleteById(long Id) {
        if (!productSizesRepository.existsById(Id))
            throw new ResourceNotFoundException("Size is not found");
        productSizesRepository.deleteById(Id);
        return HttpStatus.NO_CONTENT;
    }

}