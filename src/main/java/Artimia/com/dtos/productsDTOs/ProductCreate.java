package Artimia.com.dtos.productsDTOs;

import java.math.BigDecimal;

import Artimia.com.enums.Style;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductCreate(
                @NotBlank(message = "Product name is required") @Size(min = 2, max = 30) String productName,
                @NotNull(message = "The price must not be null") @Positive(message = "The price must be positive") BigDecimal Price,
                @NotNull(message = "the quantity of the product cannot be null") @PositiveOrZero(message = "the quantity of the product cannot be negative") int quantity,
                @NotBlank(message = "Description is required") @Size(min = 20, max = 1000) String description,
                @NotNull(message = "The style cannot be null") Style style) {
}
