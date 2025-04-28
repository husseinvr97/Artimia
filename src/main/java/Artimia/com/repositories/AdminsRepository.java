package Artimia.com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Artimia.com.entities.Admins;

public interface AdminsRepository extends JpaRepository<Admins, String> 
{
    Optional<Admins> findByUsername(String username);
}

