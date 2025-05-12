package Artimia.com.dtos.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import Artimia.com.enums.OrderStatus;

public record GetDTO(
        Long orderId,
        Long userId,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        OrderStatus status,
        String addressLine1,
        String governorate,
        String city
) {}
