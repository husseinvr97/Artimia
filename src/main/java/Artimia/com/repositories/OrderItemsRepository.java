package Artimia.com.repositories;

import Artimia.com.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> 
{

    List<OrderItems> findByOrderOrderID(Long orderId);
    List<OrderItems> findByProductProductId(Long productId);

    @Modifying
    @Query("UPDATE OrderItems oi SET oi.quantity = :newQuantity WHERE oi.orderItemId = :id")
    int setQuantity(@Param("id") Long itemId, @Param("newQuantity") Long newQuantity);
 
    @Modifying
    @Query("UPDATE OrderItems oi SET oi.unitPrice = :newPrice WHERE oi.orderItemId = :id")
    int setUnitPrice(@Param("id") Long itemId, @Param("newPrice") BigDecimal newPrice);

    @Query("SELECT oi FROM OrderItems oi JOIN FETCH oi.product JOIN FETCH oi.size WHERE oi.order.orderID = :orderID")
    List<OrderItems> findDetailedByOrderId(Long orderId);
}