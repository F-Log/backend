package com.f_log.flog.dietfood.repository;

import com.f_log.flog.dietfood.domain.DietFood;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietFoodRepository extends JpaRepository<DietFood, UUID> {
    Optional<DietFood> findByDietfoodUuidAndIsDeletedFalse(UUID dietfoodUuid);
}
