package Artimia.com.mapper;

import Artimia.com.dtos.productsizesDTOs.ProductSizesGet;
import Artimia.com.entities.ProductSizes;

public class ProductSizesMapper 
{
    public static ProductSizesGet convertToGetDTO(ProductSizes productSize) 
    {
        return new ProductSizesGet(
            productSize.getSizeId(),
            productSize.getProduct().getProductId(),
            productSize.getSize(),
            productSize.getLength(),
            productSize.getWidth(),
            productSize.getQuantity(),
            productSize.getAdditionalPrice()
        );
    }
}
