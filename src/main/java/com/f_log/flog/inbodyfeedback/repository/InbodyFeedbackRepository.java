package com.f_log.flog.inbodyfeedback.repository;

import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.inbodyfeedback.domain.InbodyFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InbodyFeedbackRepository extends JpaRepository<InbodyFeedback, Long> {
    Optional<InbodyFeedback> findByUuidAndIsDeletedFalse(UUID uuid);
    boolean existsByInbodyAndIsDeletedFalse(Inbody inbody);
}
