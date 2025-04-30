package Artimia.com.entities;

import Artimia.com.enums.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
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
    )
)
public class ProductSizes 
{

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id", updatable = false)
    private Long sizeId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false, length = 6)
    private Size size;

    @NotNull
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal length;

    @NotNull
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal width;

    @NotNull
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer quantity;

    @NotNull
    @Column(name = "additional_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal additionalPrice; 
    
}
