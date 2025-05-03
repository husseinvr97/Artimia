package Artimia.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItems 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false,referencedColumnName = "order_id")
    @NotNull(message = "Order must be specified")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, updatable = false,referencedColumnName = "product_id")
    @NotNull(message = "Product must be specified")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false, updatable = false,referencedColumnName = "size_id")
    @NotNull(message = "Size must be specified")
    private ProductSizes size;

    @Positive(message = "Quantity must be positive")
    @Column(nullable = false)
    private Long quantity; 

    @Positive(message = "unit price must be positive")
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
}