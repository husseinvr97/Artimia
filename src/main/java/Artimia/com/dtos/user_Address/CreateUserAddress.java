package Artimia.com.dtos.user_Address;

public record CreateUserAddress
(
        Long userId,
        String addressLine1,
        String city,
        String state,
        String postalCode
) {}
