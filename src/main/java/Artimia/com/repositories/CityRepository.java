package Artimia.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Artimia.com.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>
{
    boolean existsByGid(String gid);
}
