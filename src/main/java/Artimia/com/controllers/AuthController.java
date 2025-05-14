package Artimia.com.controllers;

import org.springframework.security.authentication.BadCredentialsException;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.entities.CustomUserDetails;
import Artimia.com.entities.Users;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.UserRepository;
import Artimia.com.services.TokenServices;
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
public class AuthController {
        private final AuthenticationManager authenticationManager;
        private final TokenServices tokenServices;
        private final UserRepository userRepository;

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws BadCredentialsException {
                Authentication authentication = authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.email(),
                                                request.password()));
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                Users user = userRepository.findByEmail(request.email())
                                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
                return new ResponseEntity<>(
                                new AuthResponse("login successful",
                                                new String(tokenServices.generateToken(userDetails)), user.getUserId(),
                                                user.getRole().name()),
                                HttpStatus.OK);
        }

        @ResponseStatus(HttpStatus.UNAUTHORIZED)
        @ExceptionHandler(BadCredentialsException.class)
        public ErrorResponse handleBadCredentials(BadCredentialsException ex) {
                return new ErrorResponse(ex.getMessage());
        }

        public record LoginRequest(String email, String password) {
        }

        public record AuthResponse(String message, String token, Long userId, String role) {
        }
}