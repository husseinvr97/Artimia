package Artimia.com.dtos.admins;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminUpdate
(
        @Email
        String email,
        
        @Pattern(regexp = "^\\+?[0-9]{8,15}$")
        String phoneNumber,
        
        @Size(min = 8)
        String password
) {}
