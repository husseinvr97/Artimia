package Artimia.com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Artimia.com.entities.City;

@Repository 
public interface CityRepository extends JpaRepository<City, Long>
{
    Optional<City> findByCityId(Long cityId);
    Optional<City> findByNameEn(String nameEn);
    boolean existsByNameEn(String nameEn);
    boolean existsByCityId(Long cityId);
}
