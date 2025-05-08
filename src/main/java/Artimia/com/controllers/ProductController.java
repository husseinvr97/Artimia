package Artimia.com.controllers;

import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.enums.Style;
import Artimia.com.services.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController 
{

    private final ProductService productService;

@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<?> createProduct(@RequestPart("product") ProductCreate productCreate, @RequestPart("image") MultipartFile image)
{


    if(image != null)
    {
        System.out.println("sssssss");
    }
    try 
    {
        return new ResponseEntity<>(productService.createProduct(productCreate,image), HttpStatus.CREATED);
    } catch (IOException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @GetMapping
    public Page<ProductGet> getAllProducts(Pageable pageable)
    {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductGet getProductById(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public Page<ProductGet> searchProducts(
            @RequestParam(required = false) Style style,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {
        return productService.searchProducts(style, minPrice, maxPrice, pageable);
    }

    @GetMapping("/summaries")
    public Page<Object[]> getProductSummaries(Pageable pageable) 
    {
        return productService.getProductSummaries(pageable);
    }

    @GetMapping("/top")
    public List<ProductGet> getTopProducts() 
    {
        return productService.getTopProducts();
    }

    @PatchMapping("/{id}/purchase")
    public void recordPurchase(@PathVariable Long id) 
    {
        productService.incrementTimesBought(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
    }

    public record ErrorResponse(String message) {}
}