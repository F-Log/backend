package com.f_log.flog.diet.repository;

import com.f_log.flog.diet.domain.Diet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Optional<Diet> findByDietUuidAndIsDeletedFalse(UUID dietUuid);
}
