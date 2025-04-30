package Artimia.com.dtos.order;

import Artimia.com.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateDTO(
        @NotNull(message = "Status cannot be null when updating")
        OrderStatus status,
        
        @NotBlank(message = "Shipping address cannot be blank when updating")
        String shippingAddress
) {}
