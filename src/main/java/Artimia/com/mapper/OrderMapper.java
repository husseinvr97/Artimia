package Artimia.com.mapper;

import Artimia.com.dtos.order.GetDTO;
import Artimia.com.entities.Orders;

public class OrderMapper 
{
    public static GetDTO toDto(Orders order) 
    {
        return new GetDTO(
            order.getOrderID(),
            order.getUser().getUserId(),
            order.getOrderDate(),
            order.getTotalAmount(),
            order.getOrderStatus(),
            order.getUserAddress().getAddressLine1(),
            order.getUserAddress().getGovernorate().getNameEn(),
            order.getUserAddress().getCity().getNameEn(),
            order.getUserAddress().getPostalCode(),
            order.getPaymentMethod()
        ); 
    }
}