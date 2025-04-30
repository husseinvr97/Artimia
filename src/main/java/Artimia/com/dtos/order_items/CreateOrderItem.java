package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderItem
(
        @NotNull(message = "Order ID cannot be null")
        Long orderId,

        @NotNull(message = "Product ID cannot be null")
        Long productId,

        @NotNull(message = "Size ID cannot be null")
        Long sizeId,

        @Positive(message = "Quantity must be positive")
        @NotNull(message = "Quantity cannot be null")
        Integer quantity,

        @Positive(message = "Unit price must be positive")
        @NotNull(message = "Unit price cannot be null")
        BigDecimal unitPrice
    ) {}
