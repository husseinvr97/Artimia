package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.productsDTOs.ProductCreate;
import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.enums.Style;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    try 
    {
        return new ResponseEntity<>(productService.createProduct(productCreate,image), HttpStatus.CREATED);
    } catch (IOException e) 
    {
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

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImageFile(@PathVariable String imageName) throws IOException 
    {
        // Set the absolute base path to your uploads directory
        Path uploadsDir = Paths.get("E:/Artimia/uploads").toAbsolutePath().normalize();

        // Resolve the full path of the requested image
        Path imagePath = uploadsDir.resolve(imageName).normalize();

        // Ensure the file exists and is within the allowed directory (security check)
        if (!Files.exists(imagePath) || !imagePath.startsWith(uploadsDir)) 
        {
            throw new ResourceNotFoundException("Image not found or access denied: " + imageName);
        }

        // Determine content type
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) 
        {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // Prepare file as a resource
        InputStreamResource resource = new InputStreamResource(new FileInputStream(imagePath.toFile()));

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
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
}