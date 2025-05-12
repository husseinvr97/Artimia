package Artimia.com.repositories;

import Artimia.com.entities.Products;
import Artimia.com.entities.ProductSizes;
import Artimia.com.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSizesRepository extends JpaRepository<ProductSizes, Long> 
{

    List<ProductSizes> findByProduct(Products product);
    
    @Query("SELECT ps FROM ProductSizes ps WHERE ps.product.productId = :productId")
    List<ProductSizes> findByProductId(@Param("productId") Long productId);

    @Query("SELECT ps FROM ProductSizes ps JOIN FETCH ps.product WHERE ps.product.productId = :productId")
    Optional<List<ProductSizes>> findAllByProductId(@Param("productId") Long productId);

    @Modifying
@Query("""
    UPDATE ProductSizes p SET 
        p.size = CASE WHEN :size IS NOT NULL THEN :size ELSE p.size END,
        p.length = CASE WHEN :length IS NOT NULL AND :length > 0.00 THEN :length ELSE p.length END,
        p.width = CASE WHEN :width IS NOT NULL AND :width > 0.00 THEN :width ELSE p.width END,
        p.quantity = CASE WHEN :quantity IS NOT NULL AND :quantity > 0 THEN :quantity ELSE p.quantity END,
        p.additionalPrice = CASE WHEN :additionalPrice IS NOT NULL AND :additionalPrice > 0.00 THEN :additionalPrice ELSE p.additionalPrice END
    WHERE p.sizeId = :id
""")
void update(
    @Param("id") Long id,
    @Param("size") Size size,
    @Param("length") BigDecimal length,
    @Param("width") BigDecimal width,
    @Param("quantity") Long quantity,
    @Param("additionalPrice") BigDecimal additionalPrice
);

    boolean existsByProductAndSize(Products product, Size size);
    boolean existsByLengthAndWidth(BigDecimal length, BigDecimal width);
}