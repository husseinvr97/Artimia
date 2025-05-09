package Artimia.com.services;

import Artimia.com.entities.CustomUserDetails;
import Artimia.com.entities.Users;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService 
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException
    {
        Users user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.findByPhoneNumber(email)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return new CustomUserDetails(user.getEmail(),user.getPasswordHash(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())),user.getRole().name());

    }
}