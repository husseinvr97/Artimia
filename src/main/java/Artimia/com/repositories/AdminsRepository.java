package Artimia.com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Artimia.com.entities.Admins;

public interface AdminsRepository extends JpaRepository<Admins, String> 
{
    Optional<Admins> findByEmail(String email);
    Optional<Admins> findByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}

