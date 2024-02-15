package com.f_log.flog.exercise.repository;

import com.f_log.flog.exercise.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByMemberUuidAndIsDeletedFalse(UUID memberUuid);
}
