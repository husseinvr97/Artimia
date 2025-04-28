package Artimia.com.dtos.productsDTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import Artimia.com.enums.Style;

public record ProductGet
(
    String productName,
    Long Id,
    BigDecimal Price,
    String description,
    Style style,
    String imageUrl,
    int timesBought,
    LocalDateTime dateCreated
) {}
