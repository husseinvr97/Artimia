package Artimia.com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import Artimia.com.enums.Style;

@Getter
@Setter
@Entity
@Table(name = "products", indexes = {
        @Index(name = "style_index", columnList = "style"),
        @Index(name = "base_price_index", columnList = "base_price")
})
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @Column(name = "name")
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters")
    private String productName;

    @NotNull(message = "The price cannot be null")
    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal Price;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "Description must not be blank")
    @Size(min = 25, max = 1000, message = "Description must be between 25 and 1000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private Style style;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductSizes> sizes;

    @NotNull(message = "the image url cannot be null")
    @Column(name = "main_image_url", unique = true)
    private String imageUrl;

    @NotNull(message = "the quantity of the product cannot be null")
    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "times_bought", columnDefinition = "INT DEFAULT 0")
    private int timesBought = 0;
}
