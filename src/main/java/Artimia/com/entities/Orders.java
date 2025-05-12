package Artimia.com.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import Artimia.com.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders",indexes = {
    @Index(name = "order_status_index", columnList = "status"),
    @Index(name = "order_date_index",columnList = "order_date")
})
public class Orders 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Long orderID;

    @NotNull(message = "The user Id cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user; 

    @NotNull(message = "The order date cannot be null")
    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @NotNull(message = "The order item id cannot be null")
    @OneToMany(mappedBy = "order") 
    private List<OrderItems> orderItems;

    @NotNull(message = "the total amount cannot be null")
    @Column(name = "total_amount",precision = 10,scale = 2)
    private BigDecimal totalAmount;
 
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @NotNull(message = "user address cannot be null")
    @OneToOne
    @JoinColumn(name = "user_address_id",referencedColumnName = "address_id")
    private UserAddress userAddress;
}
