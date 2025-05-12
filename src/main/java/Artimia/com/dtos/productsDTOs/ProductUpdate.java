package Artimia.com.dtos.productsDTOs;

import java.math.BigDecimal;

import Artimia.com.enums.Style;

public record ProductUpdate(
     String productName,
     BigDecimal Price,
     String description,
     Style style
) {}
