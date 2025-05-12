package Artimia.com.dtos.order;

import Artimia.com.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateDTO( 
    @NotNull(message = "OrderId cannot be null")
    @Positive(message ="Invalid orderId")
    Long userId,

    @NotNull(message = "the list of items ID cannot be null")
    @Valid
    Long[] orderItemsIds,

    @NotNull(message = "The order status cannot be null")
    OrderStatus status,

    @NotBlank(message = "The address line cannot be null")
    @Size(min = 20 ,max = 250,message = "The address line must be between 20 and 250 charatcers")
    String addressLine1,

    @NotBlank(message = "The governorate name line cannot be null")
    @Size(min = 1 ,max = 25,message = "The governorate name must be between 1 and 25 charatcers")
    String governorateName,

    @NotBlank(message = "The city name line cannot be null")
    @Size(min = 1 ,max = 25,message = "The city name must be between 20 and 250 charatcers")
    String cityName
) {}
