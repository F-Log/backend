package com.f_log.flog.dailydietfeedback.repository;

import com.f_log.flog.dailydietfeedback.domain.DailyDietFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyDietFeedbackRepository extends JpaRepository<DailyDietFeedback, UUID> {

    Optional<DailyDietFeedback> findByDietfeedbackUuidAndIsDeletedFalse(UUID dietfeedbackUuid);
}
