package Artimia.com.dtos.productsizesDTOs;

import Artimia.com.enums.Size;
import java.math.BigDecimal;

public record ProductSizesGet(
    Long sizeId,
    Long productId,
    Size size,
    BigDecimal length,
    BigDecimal width,
    Integer quantity,
    BigDecimal additionalPrice
) {}