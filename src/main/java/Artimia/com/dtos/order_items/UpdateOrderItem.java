package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public record UpdateOrderItem
(
    @Positive(message = "Quantity must be positive")
    Integer quantity,

    @Positive(message = "Unit price must be positive")
    BigDecimal unitPrice
) {}
