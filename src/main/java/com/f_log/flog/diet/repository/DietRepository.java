package com.f_log.flog.diet.repository;

import com.f_log.flog.diet.domain.Diet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Optional<Diet> findByDietUuidAndIsDeletedFalse(UUID dietUuid);
    List<Diet> findByMealDateAndMemberUuid(LocalDate mealDate, UUID memberId);
}
