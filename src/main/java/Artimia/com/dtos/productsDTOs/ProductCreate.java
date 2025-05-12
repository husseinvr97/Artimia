package Artimia.com.dtos.productsDTOs;

import java.math.BigDecimal;


import Artimia.com.enums.Style;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductCreate
(
    @NotBlank(message = "Product name is required")@Size(min = 2 , max = 30)
    String productName,
    @NotNull(message = "The price must not be null")
    @Positive(message = "The price must be positive")
    BigDecimal Price,
    @NotBlank(message = "Description is required")@Size(min = 50 , max = 1000)
    String description,
    @NotNull(message = "The style cannot be null")
    Style style
) 
{}
