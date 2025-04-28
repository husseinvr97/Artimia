package Artimia.com.dtos.productsizesDTOs;

import java.math.BigDecimal;

import Artimia.com.entities.Products;
import Artimia.com.enums.Size;
import jakarta.validation.constraints.NotNull;

public record ProductSizesCreateGet
(
    @NotNull(message = "Product cannot be null")
    Products product,
    @NotNull(message = "Size cannot be null")
    Size size,
    @NotNull(message = "length cannot be null")
    BigDecimal length,
    @NotNull(message = "Size cannot be null")
    BigDecimal width,
    @NotNull(message = "Size cannot be null")
    Integer quantity,
    @NotNull(message = "Size cannot be null")
    BigDecimal additionalPrice
) {}
