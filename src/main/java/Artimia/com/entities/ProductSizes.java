package Artimia.com.entities;

import Artimia.com.enums.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "product_sizes",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"product_id","size"},
        name = "unique_product_size"
    ),indexes = 
    {
        @Index(name = "length_index",columnList = "length"),
        @Index(name = "width_index",columnList = "width"),
        @Index(name = "size_index",columnList = "size")        
    }
)
public class ProductSizes 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id", updatable = false)
    private Long sizeId;

    @ManyToOne
    @NotNull(message = "product ID cannot be null")
    @JoinColumn(name = "product_id")
    private Products product;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The size of the product cannot be null")
    @Column(name = "size")
    private Size size = Size.SMALL;

    @NotNull(message = "length cannot be null")
    @Column(name = "length", precision = 5, scale = 2)
    private BigDecimal length;

    @NotNull(message = "width cannot be null")
    @Column(name = "width", precision = 5, scale = 2)
    private BigDecimal width;

    @NotNull(message = "quantity cannot be null")
    @Column(name = "quantity", columnDefinition = "INT DEFAULT 0")
    private Integer quantity = 0;

    @NotNull(message = "additional price cannot be null")
    @Column(name = "additional_price", precision = 10, scale = 2)
    private BigDecimal additionalPrice;
    
}
