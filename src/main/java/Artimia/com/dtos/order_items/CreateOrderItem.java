package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

public record CreateOrderItem
(
        Long orderId,
        Long productId,
        Long sizeId,
        Long quantity,
        BigDecimal unitPrice
) {}
