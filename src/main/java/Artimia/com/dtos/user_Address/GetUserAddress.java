package Artimia.com.dtos.user_Address;

public record GetUserAddress(
    Long addressId,
    Long userId,
    String addressLine1,
    String city,
    String state
) {}
