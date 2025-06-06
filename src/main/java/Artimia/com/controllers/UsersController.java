package Artimia.com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import Artimia.com.dtos.userDTOs.UserCreate;
import Artimia.com.dtos.userDTOs.UserGet;
import Artimia.com.entities.Users;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.exceptions.WeakPasswordException;
import Artimia.com.exceptions.WhiteSpaceException;
import Artimia.com.repositories.UserRepository;
import Artimia.com.services.UserServices;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserRepository userRepository;
    private final UserServices userServices;

    @PostMapping
    public ResponseEntity<UserGet> createUser(@RequestBody @Valid UserCreate request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already registered");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("Phone number already registered");
        }
        if (!userServices.IsValidPassword(request.password()))
            throw new WeakPasswordException("Your password is weakw");

        Users newUser = new Users();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPhoneNumber(request.phoneNumber());
        newUser.setPasswordHash(userServices.hashPassword(request.password()));

        Users savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(mapToDto(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserGet> getUser(@PathVariable Long userId) {
        return userRepository.findById(userId).map(user -> ResponseEntity.ok(mapToDto(user)))
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @GetMapping("/email/{userEmail}")
    public ResponseEntity<UserGet> getUserByEmail(@PathVariable String userEmail) throws ResourceNotFoundException {
        return userRepository.findByEmail(userEmail).map(user -> ResponseEntity.ok(mapToDto(user)))
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserGet> updateUser(@PathVariable Long userId, @Valid @RequestBody UserCreate request) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }

        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndUserIdNot(request.email(), userId)) {
                throw new DuplicateResourceException("Email already registered");
            }
            user.setEmail(request.email());
        }
        if (request.phoneNumber() != null && !request.phoneNumber().equals(user.getPhoneNumber())) {
            if (userRepository.existsByPhoneNumberAndUserIdNot(request.phoneNumber(), userId)) {
                throw new DuplicateResourceException("Phone number already registered");
            }
            user.setPhoneNumber(request.phoneNumber());
        }
        if (request.password() != null) {
            try {
                if (!userServices.IsValidPassword(request.password()))
                    throw new WeakPasswordException("Your password is weak");
                user.setPasswordHash(userServices.hashPassword(request.password()));
            } catch (WhiteSpaceException e) {
                throw new WeakPasswordException(e.getMessage());
            }
        }
        Users updatedUser = userRepository.save(user);
        return ResponseEntity.ok(mapToDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

    private UserGet mapToDto(Users user) {
        return new UserGet(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getDateCreated(),
                user.getRole().name());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class)
    public ErrorResponse handleDuplicate(DuplicateResourceException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ WeakPasswordException.class, WhiteSpaceException.class })
    public ErrorResponse handleWeakPassword(RuntimeException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        return new ErrorResponse(errors.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> {
            errors.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
        });
        return new ErrorResponse(errors.toString());
    }

    public record ErrorResponse(String message) {
    }
}
