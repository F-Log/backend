package com.f_log.flog.dietfood.repository;

import com.f_log.flog.dietfood.domain.DietFood;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietFoodRepository extends JpaRepository<DietFood, Long> {
    Optional<DietFood> findByIdAndIsDeletedFalse(Long id);
}
