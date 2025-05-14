package Artimia.com.dtos.userDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @NotBlank(message = "the first name is required") @Size(min = 2, max = 30, message = "the first name must be between 2 and 30 characters") @Pattern(regexp = "^(?=.{2,30}$)\\p{L}+(?:[' -]\\p{L}+)*$", message = "Invalid first name") String firstName,
        @NotBlank(message = "the last name is required ") @Size(min = 2, max = 30, message = "th last name must be between 2 and 30 characters") @Pattern(regexp = "^(?=.{2,30}$)\\p{L}+(?:[' -]\\p{L}+)*$", message = "Invalid last name") String lastName,

        @Email(message = "invalid Email") @Size(min = 10, max = 100, message = "email must be between 30 and 100 characters") String email,

        @NotEmpty(message = "phone number cannot be empty") @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "invalid phone number") String phoneNumber,

        @NotBlank(message = "The password is required") @Size(min = 8, max = 30, message = "Password Character must be between 2 and 30 characters") String password) {
}
