package com.f_log.flog.food.repository;

import com.f_log.flog.food.domain.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByIdAndIsDeletedFalse(Long id);
    List<Food> findByFoodNameAndIsDeletedFalse(String foodName);

}
