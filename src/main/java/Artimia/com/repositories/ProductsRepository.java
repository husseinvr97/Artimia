package Artimia.com.repositories;

import Artimia.com.entities.Products;
import Artimia.com.enums.Style;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> 
{


    Optional<Products> findByProductName(String productName);
    Page<Products> findByStyle(Style style, Pageable pageable);
    boolean existsByProductName(String productName);
    
    @Query("SELECT p FROM Products p WHERE " +
           "(:style IS NULL OR p.style = :style) AND " +
           "(p.Price BETWEEN :minPrice AND :maxPrice)")
    Page<Products> searchProducts(
        Style style,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Pageable pageable
    );

    @Query("SELECT p FROM Products p JOIN FETCH p.sizes WHERE p.productId = :id")
    Optional<Products> findByIdWithSizes(Long id);

    @Modifying
    @Query("UPDATE Products p SET p.timesBought = p.timesBought + 1 WHERE p.productId = :id")
    void incrementTimesBought(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Products p SET p.productName = productName WHERE p.productId = :id")
    void updateProductName(@Param("productName") String productName , @Param("id") Long id);

    @Modifying
    @Query("UPDATE Products p SET p.Price = Price WHERE p.productId = :id")
    void updateProductPrice(@Param("Price") BigDecimal Price , @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Products p SET p.description = :description WHERE p.productId = :id")
    void updateProductDescription(@Param("description") String description , @Param("id") Long id);

    @Modifying
    @Query("UPDATE Products p SET p.style = style WHERE p.productId = :id")
    void updateProductStyle(@Param("style") Style style , @Param("id") Long id);

    List<Products> findTop10ByOrderByTimesBoughtDesc();
}