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
    Optional<ProductSizes> findByProductAndSize(Products product, Size size);
    
    @Query("SELECT ps FROM ProductSize ps WHERE ps.product.productId = :productId")
    List<ProductSizes> findByProductId(@Param("productId") Long productId);

    @Query("SELECT ps.quantity FROM ProductSize ps WHERE ps.size = :size AND ps.product.productId = :productId")
    Optional<Integer> findStockQuantity(@Param("productId") Long productId, @Param("size") Size size);

    @Query("SELECT ps.additionalPrice FROM ProductSize ps WHERE ps.product.productId = :productId AND ps.size = :size")
    Optional<BigDecimal> findAdditionalPrice(@Param("productId") Long productId,@Param("size") Size size);

    @Modifying
    @Query("UPDATE ProductSize ps SET ps.quantity = ps.quantity + :quantity WHERE ps.sizeId = :sizeId")
    int updateStockQuantity(@Param("sizeId") Long sizeId,@Param("quantity") int quantity);

    @Query("SELECT ps FROM ProductSize ps JOIN FETCH ps.product WHERE ps.product.productId = :productId")
    List<ProductSizes> findWithProductDetails(@Param("productId") Long productId);

    boolean existsByProductAndSize(Products product, Size size);

    @Query("SELECT ps.length, ps.width FROM ProductSize ps WHERE ps.product.productId = :productId AND ps.size = :size")
    Optional<Object[]> findDimensions(@Param("productId") Long productId,@Param("size") Size size);
}