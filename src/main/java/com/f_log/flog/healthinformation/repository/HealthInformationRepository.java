package com.f_log.flog.healthinformation.repository;

import com.f_log.flog.healthinformation.domain.HealthInformation;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {
    Optional<HealthInformation> findByMemberUuidAndIsDeletedFalse(UUID uuid);
}