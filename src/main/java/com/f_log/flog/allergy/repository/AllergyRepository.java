package com.f_log.flog.allergy.repository;

import com.f_log.flog.allergy.domain.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, UUID> {
    Optional<Allergy> findByUuidAndIsDeletedFalse(UUID id);
}
