package Artimia.com.repositories;

import Artimia.com.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> 
{
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPhoneNumber(String phoneNumber);

    Optional<Users> findByUserId(Long Id);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndUserIdNot(String email, Long userId);
    boolean existsByPhoneNumberAndUserIdNot(String phoneNumber, Long userId);
}
