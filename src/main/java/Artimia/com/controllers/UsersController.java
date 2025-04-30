package Artimia.com.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RestController
@RequestMapping("api/users")
public class UsersController 
{
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<UserGet> createUser(@Valid@RequestBody UserCreate request)
    {
        if (userRepository.existsByEmail(request.email())) 
        {
            throw new DuplicateResourceException("Email already registered");
        }
        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new DuplicateResourceException("Phone number already registered");
        }
        try 
        {
            if(!UserServices.IsValidPassword(request.password()))
            throw new WeakPasswordException();
        } catch (WhiteSpaceException e) 
        {
            throw new WeakPasswordException(e.getMessage());
        }

        Users newUser = new Users();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPhoneNumber(request.phoneNumber());
        newUser.setPasswordHash(UserServices.hashPassword(request.password()));

        Users savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(mapToDto(savedUser),HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserGet> getUser(@PathVariable Long userId)
    {
        return userRepository.findById(userId).map(user -> ResponseEntity.ok(mapToDto(user))).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserGet> updateUser(@PathVariable Long userId,@Valid @RequestBody UserCreate request) 
    {
        Users user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        if (request.firstName() != null) 
        {
            user.setFirstName(request.firstName());
        }

        if (request.lastName() != null) 
        {
            user.setLastName(request.lastName());
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) 
        {
            if (userRepository.existsByEmailAndUserIdNot(request.email(), userId)) 
            {
                throw new DuplicateResourceException("Email already registered");
            }
            user.setEmail(request.email());
        }
        if (request.phoneNumber() != null && !request.phoneNumber().equals(user.getPhoneNumber())) 
        {
            if (userRepository.existsByPhoneNumberAndUserIdNot(request.phoneNumber(), userId)) 
            {
                throw new DuplicateResourceException("Phone number already registered");
            }
            user.setPhoneNumber(request.phoneNumber());
        }
        if (request.password() != null) 
        {
            try 
            {
                if(!UserServices.IsValidPassword(request.password()))
                    throw new WeakPasswordException();
                user.setPasswordHash(UserServices.hashPassword(request.password()));
            } catch (WhiteSpaceException e) {
                throw new WeakPasswordException(e.getMessage());
            }
        }
        Users updatedUser = userRepository.save(user);
        return ResponseEntity.ok(mapToDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) 
    {
        if (!userRepository.existsById(userId)) 
        {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(userId);
    }

    
    private UserGet mapToDto(Users user) 
    {
        return new UserGet(
            user.getFirstName(),
            user.getLastName(),
            user.getPhoneNumber(),
            user.getEmail(),
            user.getDateCreated()
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class)
    public ErrorResponse handleDuplicate(DuplicateResourceException ex) 
    {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) 
    {
        return new ErrorResponse(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WeakPasswordException.class)
    public ErrorResponse handleWeakPassword(WeakPasswordException ex) 
    {
        return new ErrorResponse(ex.getMessage());
    }

    public record ErrorResponse(String message) {}
}

