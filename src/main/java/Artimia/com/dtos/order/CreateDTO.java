package Artimia.com.dtos.order;

import java.math.BigDecimal;

import Artimia.com.enums.OrderStatus;
import Artimia.com.enums.PaymentMethod;

public record CreateDTO
(
    Long userId,
    BigDecimal totalAmount,
    OrderStatus status,
    String addressLine1,
    String governorateName,
    String cityName,
    String postalCode,
    PaymentMethod paymentMethod
) {}
