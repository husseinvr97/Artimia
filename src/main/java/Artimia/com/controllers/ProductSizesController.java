// ProductSizesController.java
package Artimia.com.controllers;

import Artimia.com.dtos.productsizesDTOs.ProductSizesCreate;
import Artimia.com.dtos.productsizesDTOs.ProductSizesGet;
import Artimia.com.exceptions.DuplicateProductSizeException;
import Artimia.com.exceptions.ProductNotFoundException;
import Artimia.com.services.ProductSizesServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-sizes")
@RequiredArgsConstructor
public class ProductSizesController 
{

    private final ProductSizesServices productSizesService;

    @PostMapping
    public ResponseEntity<ProductSizesGet> createProductSize(@RequestBody @Valid ProductSizesCreate dto) 
    {
        ProductSizesGet response = productSizesService.createProductSize(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public record ErrorResponse(String message) {}

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateProductSizeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateProductSize(DuplicateProductSizeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) 
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An unexpected error occurred"));
    }
}