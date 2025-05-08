package Artimia.com.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Artimia.com.entities.Governorate;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, Long> 
{
    boolean existsByNameEn(String nameEn);
    Optional<Governorate> findByNameEn(String nameEn);
    Optional<Governorate> findByGovernorateId(Long governorateId);
    boolean existsByGovernorateId(Long governorateId);
}
