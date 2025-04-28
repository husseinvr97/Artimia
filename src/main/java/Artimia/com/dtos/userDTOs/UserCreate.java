package Artimia.com.dtos.userDTOs;

import Artimia.com.annotations.ConstraintAnnotations.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
    @NotBlank @Size(min = 2, max = 30,message = "the first name must be between 2 and 30 characters")@Pattern(regexp = "^(?=.{2,30}$)\\p{L}+(?:[' -]\\p{L}+)*$", message = "Invalid first name") 
    String firstName,
    @NotBlank @Size(min = 2, max = 30,message = "the first name must be between 2 and 30 characters")@Pattern(regexp = "^(?=.{2,30}$)\\p{L}+(?:[' -]\\p{L}+)*$", message = "Invalid last name") 
    String lastName,

    @NotBlank @Size(min = 2 ,max = 30 , message = "the username must be between 2 and 30 characters")@Pattern(regexp = "^[a-z0-9]+$")
    String username,
    @Email(message = "invalid Email")
    String email,
    
    @NotEmpty(message = "phone number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$" , message = "invalid phone number") 
    String phoneNumber,

    @NotBlank
    @StrongPassword
    String password
) {}
