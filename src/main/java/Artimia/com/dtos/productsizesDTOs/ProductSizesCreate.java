package Artimia.com.dtos.productsizesDTOs;

import Artimia.com.enums.Size;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductSizesCreate(
    @NotNull(message = "Product ID cannot be null")
    Long productId,
    @NotNull(message = "Size cannot be null")
    Size size,
    @NotNull(message = "Length cannot be null")
    BigDecimal length,
    @NotNull(message = "Width cannot be null")
    BigDecimal width,
    @NotNull(message = "Quantity cannot be null")
    Integer quantity,
    @NotNull(message = "Additional price cannot be null")
    BigDecimal additionalPrice
) {}
