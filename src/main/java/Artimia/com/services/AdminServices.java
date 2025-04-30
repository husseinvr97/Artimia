package Artimia.com.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import Artimia.com.dtos.admins.AdminGet;
import Artimia.com.dtos.admins.AdminUpdate;
import Artimia.com.entities.Admins;
import Artimia.com.exceptions.DuplicateResourceException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.repositories.AdminsRepository;

public class AdminServices 
{
    private AdminsRepository adminsRepository;
    private PasswordEncoder passwordEncoder;
    public AdminGet updateAdmin(String username, AdminUpdate dto) 
    {
        
        Admins admin = adminsRepository.findById(username)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (dto.email() != null && !dto.email().equals(admin.getEmail())) {
            if (adminsRepository.existsByEmail(dto.email())) {
                throw new DuplicateResourceException("Email already registered");
            }
            admin.setEmail(dto.email());
        }

        if (dto.phoneNumber() != null && !dto.phoneNumber().equals(admin.getPhoneNumber())) {
            if (adminsRepository.existsByPhoneNumber(dto.phoneNumber())) {
                throw new DuplicateResourceException("Phone number already registered");
            }
            admin.setPhoneNumber(dto.phoneNumber());
        }

        if (dto.password() != null) {
            admin.setPasswordHash(passwordEncoder.encode(dto.password()));
        }

        return mapToDto(adminsRepository.save(admin));
    }
    private AdminGet mapToDto(Admins admin) {
        return new AdminGet(
            admin.getEmail(),
            admin.getPhoneNumber()
        );
    }
}
