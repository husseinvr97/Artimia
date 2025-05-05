package Artimia.com.dtos.user_Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserAddress
(
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotBlank(message = "Address line 1 is required")
        String addressLine1,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Postal code is required")
        String postalCode,

        @NotBlank(message = "Country is required")
        String country

) {}
