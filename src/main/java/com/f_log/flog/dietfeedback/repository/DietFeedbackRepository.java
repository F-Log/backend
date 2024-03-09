package com.f_log.flog.dietfeedback.repository;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.dietfeedback.domain.DietFeedback;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DietFeedbackRepository extends JpaRepository<DietFeedback, UUID> {

    Optional<DietFeedback> findByDietfeedbackUuidAndIsDeletedFalse(UUID dietfeedbackUuid);
}
