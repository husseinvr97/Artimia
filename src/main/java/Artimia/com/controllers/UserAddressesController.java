package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.user_Address.CreateUserAddress;
import Artimia.com.dtos.user_Address.GetUserAddress;
import Artimia.com.dtos.user_Address.UpdateUserAddress;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.UserAddressesServices;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class UserAddressesController {
    private final UserAddressesServices userAddressesServices;

    @PostMapping
    public ResponseEntity<GetUserAddress> createAddress(@RequestBody @Valid CreateUserAddress dto) {
        return new ResponseEntity<>(userAddressesServices.createUserAddress(dto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserAddress> getAddressesByUser(@PathVariable Long userId)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(userAddressesServices.getUserAddressesByUserId(userId), HttpStatus.FOUND);
    }

    @PostMapping("import")
    public ResponseEntity<HttpStatus> importAllregions() {
        userAddressesServices.importAllRegions();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/governorate/{governorateName}")
    public ResponseEntity<List<GetUserAddress>> getAllByGovName(@PathVariable String govName) {
        return new ResponseEntity<>(userAddressesServices.getAllByGovName(govName), HttpStatus.FOUND);
    }

    @PutMapping("/{addressId}")
    public GetUserAddress updateAddress(@PathVariable Long addressId, @RequestBody UpdateUserAddress dto)
            throws MethodArgumentNotValidException, ConstraintViolationException, ResourceNotFoundException {
        return userAddressesServices.updateUserAddress(addressId, dto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

}