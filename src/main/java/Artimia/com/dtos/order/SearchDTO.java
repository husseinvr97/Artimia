package Artimia.com.dtos.order;
import java.time.LocalDateTime;
import Artimia.com.enums.OrderStatus;

public record SearchDTO
(
    Long userId,
    OrderStatus status,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
