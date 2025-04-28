package Artimia.com.repositories;

import Artimia.com.entities.Products;
import Artimia.com.enums.Style;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {

    // Basic query methods
    Optional<Products> findByProductName(String productName);
    Page<Products> findByStyle(Style style, Pageable pageable);
    
    // Custom query with filters
    @Query("SELECT p FROM Products p WHERE " +
           "(:style IS NULL OR p.style = :style) AND " +
           "(p.basePrice BETWEEN :minPrice AND :maxPrice)")
    Page<Products> searchProducts(
        Style style,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Pageable pageable
    );

    @Query("SELECT p.productId, p.productName, p.basePrice, p.imageUrl FROM Products p")
    Page<Object[]> findProductSummaries(Pageable pageable);

    @Query("SELECT p FROM Products p JOIN FETCH p.sizes WHERE p.productId = :id")
    Optional<Products> findByIdWithSizes(Long id);

    List<Products> findTop10ByOrderByTimesBoughtDesc();
    List<Products> findByDateCreatedAfter(LocalDateTime date);
    List<Products> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);
}