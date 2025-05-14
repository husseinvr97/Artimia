package Artimia.com.repositories;

import Artimia.com.entities.Users;
import Artimia.com.entities.UserAddress;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
    Optional<UserAddress> findByUser(Users user);

    Optional<UserAddress> findByUserUserId(Long userId);

    Optional<List<UserAddress>> findAllByGovernorateNameEn(String govName);
}
