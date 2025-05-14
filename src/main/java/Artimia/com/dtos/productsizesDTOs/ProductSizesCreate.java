package Artimia.com.dtos.productsizesDTOs;

import Artimia.com.enums.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductSizesCreate(
        @NotBlank(message = "Product name cannot be null") @Pattern(regexp = "^[A-Za-z0-9À-ÿ &()’'\".,-]{2,100}$", message = "Invalid product name") String productName,
        @NotNull(message = "Size cannot be null") Size size,
        @NotNull(message = "Length cannot be null") @Positive(message = "length must be positive") BigDecimal length,
        @NotNull(message = "Width cannot be null") @Positive(message = "width must be positive") BigDecimal width,
        @NotNull(message = "Quantity cannot be null") @Positive(message = "quantity must be positive") Integer quantity,
        @NotNull(message = "Additional price cannot be null") @PositiveOrZero(message = "additional price must be positive") BigDecimal additionalPrice) {
}
