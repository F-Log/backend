package com.f_log.flog.diet.repository;

import com.f_log.flog.diet.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findDietByUuid(UUID uuid);
}
