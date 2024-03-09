package com.f_log.flog.food.repository;

import com.f_log.flog.food.domain.Food;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, UUID> {
    @Query("SELECT f FROM Food f WHERE ((f.foodName LIKE %:foodName% AND f.memberUuid = :memberUuid) OR (f.foodName LIKE %:foodName% AND f.memberUuid IS NULL)) AND f.isDeleted = false")
    Page<Food> searchByFoodNameAndUser(String foodName, UUID memberUuid, Pageable pageable);
    Optional<Food> findByFoodUuidAndIsDeletedFalse(UUID foodUuid);
    List<Food> findByFoodNameAndIsDeletedFalse(String foodName);
}
