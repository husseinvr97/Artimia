package Artimia.com.mapper;

import Artimia.com.dtos.order_items.GetOrderItem;
import Artimia.com.dtos.order_items.UpdateOrderItem;
import Artimia.com.entities.OrderItems;

public class OrderItemMapper 
{
    public static GetOrderItem toGetDto(OrderItems item) 
    { 
        return new GetOrderItem(
            item.getOrderItemId(),
            item.getOrder().getOrderID(),
            item.getProduct().getProductId(),
            item.getSize().getSizeId(),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getProduct().getProductName(),
            item.getSize().getSize().name() // this is for Enum size which exists in product sizes
        );
    }
    public static UpdateOrderItem toUpdateDto(OrderItems item)
    {
        return new UpdateOrderItem(
            item.getQuantity(),
            item.getUnitPrice()
        );
    }
}