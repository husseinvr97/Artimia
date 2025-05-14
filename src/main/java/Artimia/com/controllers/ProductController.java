package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.dtos.productsDTOs.ProductUpdate;
import Artimia.com.enums.Style;
import Artimia.com.exceptions.InvalidNameException;
import Artimia.com.exceptions.NegativeOrZeroException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.exceptions.StringLengthException;
import Artimia.com.services.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductGet> createProduct(@RequestPart("product") @Valid ProductCreate productCreate,
            @RequestPart("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(productService.createProduct(productCreate, image), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<ProductGet> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGet> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.FOUND);
    }

    @GetMapping("/{product_name}")
    public ResponseEntity<ProductGet> getProductById(@PathVariable String productName)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(productService.getProductByName(productName), HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public Page<ProductGet> searchProducts(
            @RequestParam(required = false) Style style,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {
        return productService.searchProducts(style, minPrice, maxPrice, pageable);
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String imageName) throws IOException {
        Path uploadsDir = Paths.get("D:/projects/Artimia/uploads").toAbsolutePath().normalize();

        Path imagePath = uploadsDir.resolve(imageName).normalize();

        if (!Files.exists(imagePath) || !imagePath.startsWith(uploadsDir)) {
            throw new ResourceNotFoundException("Image not found or access denied: " + imageName);
        }

        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(imagePath.toFile()));

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/top")
    public ResponseEntity<List<ProductGet>> getTopProducts() {
        return new ResponseEntity<>(productService.getTopProducts(), HttpStatus.OK);
    }

    @PatchMapping("/{id}/purchase")
    public ResponseEntity<?> recordPurchase(@PathVariable Long id) {
        productService.incrementTimesBought(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> updateProduct(@RequestBody ProductUpdate productUpdate, @PathVariable Long id)
            throws ResourceNotFoundException, InvalidNameException, NegativeOrZeroException, StringLengthException {
        return new ResponseEntity<>(productService.updateProduct(productUpdate, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ ResourceNotFoundException.class, InvalidNameException.class, NegativeOrZeroException.class,
            StringLengthException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleIOException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}