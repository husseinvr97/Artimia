package Artimia.com.dtos.user_Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserAddress(
                @NotNull(message = "UserId cannot be null") Long userId,
                @NotBlank(message = "Address line 1 is required") String addressLine1,
                @NotBlank(message = "City is required") String cityName,
                @NotBlank(message = "Governorate is required") String governorateName) {
}