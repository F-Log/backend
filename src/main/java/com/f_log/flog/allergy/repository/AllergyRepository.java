package com.f_log.flog.allergy.repository;

import com.f_log.flog.allergy.domain.Allergy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, UUID> {
    Optional<Allergy> findByUuidAndIsDeletedFalse(UUID id);

    @Query("SELECT a FROM Allergy a WHERE a.member.uuid = :memberUuid AND a.isDeleted = false ORDER BY a.createdAt DESC")
    Page<Allergy> findTopByMemberUuidOrderByCreatedAtDesc(UUID memberUuid, Pageable pageable);
}
