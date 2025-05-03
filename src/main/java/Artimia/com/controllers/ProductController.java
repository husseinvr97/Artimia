// Controller Layer
package Artimia.com.controllers;

import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.enums.Style;
import Artimia.com.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController 
{

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductGet createProduct(@Valid @RequestBody ProductCreate productCreate) {
        return productService.createProduct(productCreate);
    }

    @GetMapping
    public Page<ProductGet> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductGet getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
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
    public Page<Object[]> getProductSummaries(Pageable pageable) {
        return productService.getProductSummaries(pageable);
    }

    @GetMapping("/top")
    public List<ProductGet> getTopProducts() {
        return productService.getTopProducts();
    }

    @PatchMapping("/{id}/purchase")
    public void recordPurchase(@PathVariable Long id) {
        productService.incrementTimesBought(id);
    }
}