package Artimia.com.services;

import Artimia.com.entities.Users;
import Artimia.com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService 
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username)
                .orElseGet(() -> userRepository.findByPhoneNumber(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        return new User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.emptyList()
        );
    }
}