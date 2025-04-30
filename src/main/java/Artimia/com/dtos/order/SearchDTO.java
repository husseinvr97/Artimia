package Artimia.com.dtos.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import Artimia.com.enums.OrderStatus;
import jakarta.validation.constraints.PositiveOrZero;

public record SearchDTO
(
    Long userId,
    OrderStatus status,
    @PositiveOrZero BigDecimal minAmount,
    @PositiveOrZero BigDecimal maxAmount,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
