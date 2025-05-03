package Artimia.com.repositories;

import Artimia.com.entities.Users;
import Artimia.com.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> 
{

    List<UserAddress> findByUser(Users user);
    
    @Query("SELECT ua FROM UserAddress ua WHERE " +
           "ua.user.userId = :userId AND " +
           "(lower(ua.city) LIKE lower(concat('%', :city, '%')) OR :city IS NULL)")
    List<UserAddress> searchByUserAndCity(@Param("userId") Long userId, 
                                        @Param("city") String city);
}
