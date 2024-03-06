package com.f_log.flog.food.service;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.FoodRequestDto;
import com.f_log.flog.food.repository.FoodRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    public Food saveFood(FoodRequestDto foodRequestDto) {
        Food food = foodRequestDto.toEntity();
        return foodRepository.save(food);
    }

    public Food findFood(Long foodId) {
        return foodRepository.findByIdAndIsDeletedFalse(foodId).orElse(null);
    }

    @Transactional
    public boolean updateFood(Long id, FoodRequestDto foodRequestDto) {
        Food food = foodRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (food != null) {
            food.updateCholesterol(foodRequestDto.getCholesterol());
            food.updateCarbohydrate(foodRequestDto.getCarbohydrate());
            food.updateFoodName(foodRequestDto.getFoodName());
            food.updateFat(foodRequestDto.getFat());
            food.updateProtein(foodRequestDto.getProtein());
            food.updateSodium(foodRequestDto.getSodium());
            food.updateCalories(foodRequestDto.getCalories());
            food.updateSugars(foodRequestDto.getSugars());
            food.updateServingSize(foodRequestDto.getServingSize());
            food.updateServingUnit(foodRequestDto.getServingUnit());
            food.updateSaturatedFat(foodRequestDto.getSaturatedFat());
            food.updateTransFat(foodRequestDto.getTransFat());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteFood(Long id) {
        Food food = foodRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (food != null) {
            food.setDeleted();
            foodRepository.save(food);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void saveAllFoods(List<Food> foods) {
        List<Food> filteredFoods = new ArrayList<>();
        for (Food food : foods) {
            Optional<Food> existingFood = foodRepository.findByFoodNameAndIsDeletedFalse(food.getFoodName());
            if (!existingFood.isPresent()) {
                filteredFoods.add(food);
            }
        }
        if (!filteredFoods.isEmpty()) {
            foodRepository.saveAll(filteredFoods);
        }
    }

    public List<Food> findAllFoods(){
        return foodRepository.findAll();
    }
}

