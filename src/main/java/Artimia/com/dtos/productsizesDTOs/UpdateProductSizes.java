package Artimia.com.dtos.productsizesDTOs;

import java.math.BigDecimal;

import Artimia.com.enums.Size;

public record UpdateProductSizes(
    Size size,
    BigDecimal length,
    BigDecimal width,
    Long quantity, 
    BigDecimal additionalPrice
)
{}
