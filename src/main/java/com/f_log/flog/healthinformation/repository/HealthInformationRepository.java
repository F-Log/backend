package com.f_log.flog.healthinformation.repository;

import com.f_log.flog.healthinformation.domain.HealthInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {
    Optional<HealthInformation> findByMemberUuidAndIsDeletedFalse(UUID uuid);
}