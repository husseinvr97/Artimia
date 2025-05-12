package Artimia.com.dtos.order_items;

import java.math.BigDecimal;

public record UpdateOrderItem(
    
    Long quantity,
    BigDecimal unitPrice
) {}
