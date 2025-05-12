package Artimia.com.dtos.order;

import Artimia.com.enums.OrderStatus;

public record UpdateDTO(
        OrderStatus status,
        String addressLine1,
        String governorateName,
        String cityName
) {}
