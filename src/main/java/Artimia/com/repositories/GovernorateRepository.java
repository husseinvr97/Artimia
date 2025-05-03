package Artimia.com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Artimia.com.entities.Governorate;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, Long> 
{
    Optional<Governorate> findByGid(String gid);
    Optional<Governorate> findByNameEn(String name);
}
