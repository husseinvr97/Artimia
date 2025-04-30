package Artimia.com.dtos.admins;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminCreate
(
    @NotBlank @Size(min = 4, max = 20) 
    String username,
        
    @NotBlank @Email
    String email,
        
    @NotBlank @Pattern(regexp = "^\\+?[0-9]{8,15}$")
    String phoneNumber,
        
    @NotBlank @Size(min = 8)
    String password
) 

{}
