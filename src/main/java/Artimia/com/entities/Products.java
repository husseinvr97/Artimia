package Artimia.com.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import Artimia.com.enums.Style;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_style", columnList = "style"),
    @Index(name = "idx_created_date", columnList = "date_created")
})
public class Products 
{
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long productId;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Product name must not be blank")
    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters")
    private String productName;

    @Getter
    @Setter
    @Column(name = "base_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal Price;

    @Getter
    @Setter
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    @Size(min = 25, max = 1000 ,message = "Description must be between 25 and 1000 characters")
    private String description;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private Style style;

    @Getter
    @Setter
    @Column(name = "main_image_url", nullable = false , unique = true)
    @Pattern(regexp = "^(https?)://[^\\s/$.?#].[^\\s]*\\.(png|jpg|jpeg|gif)(\\?.*)?$",message = "Invalid image URL format")
    private String imageUrl;

    @Getter
    @Setter
    @Column(name = "times_bought", columnDefinition = "INT DEFAULT 0")
    private int timesBought = 0;

    @Getter
    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated;
}
