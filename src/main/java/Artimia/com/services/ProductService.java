package Artimia.com.services;

import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.dtos.productsDTOs.ProductUpdate;
import Artimia.com.entities.Products;
import Artimia.com.enums.Style;
import Artimia.com.exceptions.InvalidNameException;
import Artimia.com.exceptions.NegativeOrZeroException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.exceptions.StringLengthException;
import Artimia.com.mapper.ProductMapper;
import Artimia.com.repositories.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ProductGet createProduct(ProductCreate productCreate,MultipartFile image) throws IOException
    {
        Products product = new Products();
        product.setProductName(productCreate.productName());
        product.setPrice(productCreate.Price());
        product.setDescription(productCreate.description());
        product.setStyle(productCreate.style());
        if (image != null && !image.isEmpty()) 
        {
            String filename = fileStorageService.storeFile(image);
            product.setImageUrl("/uploads/" + filename);
        }
        
        Products savedProduct = productsRepository.save(product);
        return ProductMapper.convertToProductGet(savedProduct);
    }

    public Page<ProductGet> getAllProducts(Pageable pageable) 
    {
        return productsRepository.findAll(pageable).map(ProductMapper::convertToProductGet);
    }

    public ProductGet getProductById(Long id) 
    {
        return productsRepository.findById(id).map(ProductMapper::convertToProductGet).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductGet getProductByName(String productName)
    {
        return productsRepository.findByProductName(productName).map(ProductMapper::convertToProductGet).orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    public Page<ProductGet> searchProducts(Style style, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) 
    {
        return productsRepository.searchProducts(style,minPrice != null ? minPrice : BigDecimal.ZERO,maxPrice != null ? maxPrice : BigDecimal.valueOf(1000000),pageable)
        .map(ProductMapper::convertToProductGet);
    }

    public List<ProductGet> getTopProducts() 
    {
        return productsRepository.findTop10ByOrderByTimesBoughtDesc().stream().map(ProductMapper::convertToProductGet).toList();
    }

    @Transactional
    public void incrementTimesBought(Long productId) 
    {
        if(!productsRepository.existsById(productId))
            throw new ResourceNotFoundException("Product cannot be found");
        productsRepository.incrementTimesBought(productId);
    }

    @Transactional
    public void deleteProduct(Long id)
    {
        if(!productsRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");
        productsRepository.deleteById(id);
    }

    @Transactional
    public Integer updateProduct(ProductUpdate productUpdate,Long id)
    {
        if(!productsRepository.existsById(id))
            throw new ResourceNotFoundException("Product not found");
        int numberOfUpdates = 0;
        if(productUpdate.productName() != null ) 
        {
            if(!productUpdate.productName().matches("^[A-Za-z0-9À-ÿ &()’'\".,-]{2,100}$"))
                throw new InvalidNameException("Invalid product name");
            productsRepository.updateProductName(productUpdate.productName(),id);
            ++numberOfUpdates;
        }
        if(productUpdate.Price() != null)
        {
            if(productUpdate.Price().doubleValue() <= 0.0 )
                throw new NegativeOrZeroException("The product price cannot be negative or zero");
            productsRepository.updateProductPrice(productUpdate.Price(),id);
            ++numberOfUpdates;
        }
        if(productUpdate.description() != null)
        {
            if(productUpdate.description().length() > 1000 || productUpdate.description().length() < 25)
                throw new StringLengthException("The product description must be between 25 and 1000 characters");
            productsRepository.updateProductDescription(productUpdate.description(),id);
            ++numberOfUpdates;
        }
        if(productUpdate.style() != null)
        {
            productsRepository.updateProductStyle(productUpdate.style(),id);
            ++numberOfUpdates;
        }
        return numberOfUpdates;
    }

    
}