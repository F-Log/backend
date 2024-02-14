package com.f_log.flog.food.service;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.FoodRequestDto;
import com.f_log.flog.food.repository.FoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    public void saveFood(Food food) {
        foodRepository.save(food);
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

    public List<Food> findAllFoods(){
        return foodRepository.findAll();
    }
}

