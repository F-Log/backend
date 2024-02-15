package com.f_log.flog.allergy.repository;

import com.f_log.flog.allergy.domain.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {

    Optional<Allergy> findByIdAndIsDeletedFalse(Long id);
}
