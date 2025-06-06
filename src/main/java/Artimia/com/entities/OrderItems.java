package Artimia.com.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items",indexes = @Index(name = "order_item_id_index",columnList = "order_item_id"))
public class OrderItems 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @NotNull(message = "Order ID cannot be null")
    @ManyToOne
    @JoinColumn(name = "order_id", updatable = false,referencedColumnName = "order_id")
    private Orders order;

    @NotNull(message = "Product ID cannot be null")
    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false,referencedColumnName = "product_id")
    private Products product;

    @NotNull(message = "Size ID cannot be null")
    @ManyToOne
    @JoinColumn(name = "size_id",  updatable = false,referencedColumnName = "size_id")
    private ProductSizes size;

    @NotNull(message = "Quantity cannot be null")
    @Column(name = "quantity")
    private Long quantity; 

    @NotNull(message = "unit price cannot be null")
    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
}