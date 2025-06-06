package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

public record GetOrderItem
(
        Long orderItemId,
        Long orderId,
        Long productId,
        Long sizeId,
        Long quantity,
        BigDecimal unitPrice,
        String productName,
        String sizeLabel
) {}
