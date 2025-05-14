package Artimia.com.dtos.others;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record CheckOutDto(

        @NotNull(message = "Product ID cannot be null") Long productId,

        @NotNull(message = "Product Size ID cannot be null") Long productSizeId,
        BigDecimal price,
        String imageUrl,
        Long quantity) {
}
