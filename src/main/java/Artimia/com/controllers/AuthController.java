package Artimia.com.controllers;

import org.springframework.security.authentication.BadCredentialsException;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.entities.CustomUserDetails;
import Artimia.com.services.TokenServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request, HttpServletResponse response) 
    {
        try 
        {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(),request.password()));
        
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = tokenServices.generateToken(userDetails);

            return ResponseEntity.ok().body(new AuthResponse("Login successful", token));
        } 
        catch (BadCredentialsException e)
        {
            throw new BadCredentialsException("Invalid email or password");
        }
}

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentials(BadCredentialsException ex) 
    {
        return new ErrorResponse(ex.getMessage());
    }
    
    public record LoginRequest( String email, String password) {}
    public record AuthResponse(String message, String token) {}
}