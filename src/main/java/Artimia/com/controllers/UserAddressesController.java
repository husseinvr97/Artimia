package Artimia.com.controllers;

import Artimia.com.dtos.user_Address.CreateUserAddress;
import Artimia.com.dtos.user_Address.GetUserAddress;
import Artimia.com.dtos.user_Address.UpdateUserAddress;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.UserAddressesServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class UserAddressesController 
{
    private final UserAddressesServices userAddressesServices;

    @PostMapping
    public ResponseEntity<GetUserAddress> createAddress(@Valid @RequestBody CreateUserAddress dto) {
        return new ResponseEntity<>(userAddressesServices.createUserAddress(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserAddress> getAddressesByUser(@PathVariable Long userId) 
    {
        return new ResponseEntity<>(userAddressesServices.getUserAddressesByUserId(userId),HttpStatus.FOUND);
    }

    @PutMapping("/{addressId}")
    public GetUserAddress updateAddress(@PathVariable Long addressId, @Valid @RequestBody UpdateUserAddress dto) 
    {
        return userAddressesServices.updateUserAddress(addressId, dto);
    }

    @DeleteMapping("/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable Long addressId) 
    {
        userAddressesServices.deleteUserAddress(addressId);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) 
    {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    public record ErrorResponse(String message) {}
}