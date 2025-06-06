package Artimia.com.dtos.userDTOs;

import java.time.LocalDateTime;

public record UserGet
(
    String firstName,
    String lastName,
    String phoneNumber,
    String email,
    LocalDateTime dateCreated,
    String role
) {}
