package Artimia.com.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import Artimia.com.enums.PaymentMethod;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users",indexes = {
    @Index(name = "idx_order_status_index", columnList = "status")
})
public class Orders 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false , insertable = false)
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private Users user; 

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount",precision = 10,scale = 2) // precision checks are at the DTO remove them and add them there
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",length = 20)
    private OrderStatus orderstatue;

    @Column(name = "shipping_address",columnDefinition = "TEXT")
    private String shippingAddress;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;
}
