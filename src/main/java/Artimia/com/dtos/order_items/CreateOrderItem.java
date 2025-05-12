package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateOrderItem(
        @NotNull(message = "OrderId cannot be null")
        @Positive(message ="Invalid orderId")
        Long orderId,
        @NotNull(message = "productId cannot be null")
        @Positive(message ="Invalid productId")
        Long productId,
        @NotNull(message = "sizeId cannot be null")
        @Positive(message ="Invalid sizeId")
        Long sizeId,
        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "quantity cannot be negative or zero")
        Long quantity,
        @NotNull(message = "The unit price cannot be null")
        @Positive(message = "The unit price cannot be negative or zero")
        BigDecimal unitPrice
) {}
