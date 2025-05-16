package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.productsizesDTOs.ProductSizesCreate;
import Artimia.com.dtos.productsizesDTOs.ProductSizesGet;
import Artimia.com.dtos.productsizesDTOs.UpdateProductSizes;
import Artimia.com.exceptions.DuplicateProductSizeException;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ProductNotFoundException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.ProductSizesServices;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/product-sizes")
@RequiredArgsConstructor
public class ProductSizesController {

    private final ProductSizesServices productSizesService;

    @PostMapping
    public ResponseEntity<HttpStatus> createProductSize(@RequestBody @Valid ProductSizesCreate productSizesCreate)
            throws MethodArgumentNotValidException, ConstraintViolationException, DuplicateResourceException,
            ResourceNotFoundException {
        return new ResponseEntity<>(productSizesService.createProductSize(productSizesCreate));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductSizesGet>> getAllById(@PathVariable Long productId)
            throws MethodArgumentNotValidException, ConstraintViolationException, ResourceNotFoundException {
        return new ResponseEntity<>(productSizesService.getAllByProductId(productId), HttpStatus.OK);
    }

    @PutMapping("/{sizeId}")
    public ResponseEntity<HttpStatus> update(@RequestBody UpdateProductSizes updateProductSizes, @PathVariable Long Id)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(productSizesService.update(updateProductSizes, Id));
    }

    @DeleteMapping("/{sizeId}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long Id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productSizesService.deleteById(Id));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateProductSize(DuplicateProductSizeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("An unexpected error occurred"));
    }
}