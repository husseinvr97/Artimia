package Artimia.com.dtos.order;

import java.math.BigDecimal;

import Artimia.com.enums.OrderStatus;
import Artimia.com.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateDTO
(
        @NotNull(message = "User ID cannot be null")
        Long userId,
        
        @NotNull(message = "Total amount cannot be null")
        @Positive(message = "Total amount must be positive")
        BigDecimal totalAmount,
        
        @NotNull(message = "Status cannot be null")
        OrderStatus status,
        
        @NotBlank(message = "Shipping address cannot be blank")
        String shippingAddress,
        
        @NotNull(message = "Payment method cannot be null")
        PaymentMethod paymentMethod
    ) {}
