package Artimia.com.repositories;

import Artimia.com.entities.Orders;
import Artimia.com.entities.Users;
import Artimia.com.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> 
{

    List<Orders> findByUser(Users user);
    List<Orders> findByOrderStatus(OrderStatus orderStatus);
    List<Orders> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Custom query for total amount range
    @Query("SELECT o FROM Orders o WHERE o.totalAmount BETWEEN :min AND :max")
    List<Orders> findOrdersInAmountRange(@Param("min") BigDecimal minAmount, @Param("max") BigDecimal maxAmount);

    // Optimized status update
    @Modifying
    @Query("UPDATE Orders o SET o.orderStatus = :status WHERE o.orderID = :orderID")
    int updateOrderStatus(@Param("orderID") Long orderId, @Param("orderStatus") OrderStatus newStatus);

    // Complex search combining multiple fields
    @Query("SELECT o FROM Orders o WHERE " +
           "(:userId IS NULL OR o.user.userId = :userId) AND " +
           "(:status IS NULL OR o.orderStatus = :orderStatus) AND " +
           "(:minDate IS NULL OR o.orderDate >= :minDate) AND " +
           "(:maxDate IS NULL OR o.orderDate <= :maxDate)")
    List<Orders> searchOrders(
        @Param("userId") Long userId,
        @Param("orderStatus") OrderStatus status,
        @Param("minDate") LocalDateTime minDate,
        @Param("maxDate") LocalDateTime maxDate
    );

}
