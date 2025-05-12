package Artimia.com.mapper;

import Artimia.com.dtos.productsDTOs.ProductGet;
import Artimia.com.entities.Products;

public class ProductMapper 
{
    public static ProductGet convertToProductGet(Products product) 
    {
        return new ProductGet
        (
            product.getProductName(),
            product.getProductId(),
            product.getPrice(),
            product.getDescription(),
            product.getStyle(),
            product.getImageUrl(),
            product.getTimesBought()
        );
    }
}
