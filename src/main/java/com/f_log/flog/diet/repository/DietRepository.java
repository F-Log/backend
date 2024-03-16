package com.f_log.flog.diet.repository;

import com.f_log.flog.diet.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DietRepository extends JpaRepository<Diet, UUID> {
    Optional<Diet> findByDietUuidAndIsDeletedFalse(UUID dietUuid);
    List<Diet> findByMealDateAndMemberUuidAndIsDeletedFalse(LocalDate mealDate, UUID memberId);
}
