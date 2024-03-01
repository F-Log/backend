package com.f_log.flog.food.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.FoodRequestDto;
import com.f_log.flog.food.repository.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final DietRepository dietRepository;

    @Transactional
    public Food saveFood(FoodRequestDto foodRequestDto) {
        // foodRequestDto에서 dietId가 null인지 확인
        if (foodRequestDto.getDietId() == null) {
            throw new IllegalArgumentException("dietId must not be null");
        }

        // dietId로 Diet 엔티티 조회
        Diet diet = dietRepository.findById(foodRequestDto.getDietId())
                .orElseThrow(() -> new EntityNotFoundException("Diet not found with id: " + foodRequestDto.getDietId()));

        // Food 엔티티 생성
        Food food = Food.builder()
                .foodName(foodRequestDto.getFoodName())
                .carbohydrate(foodRequestDto.getCarbohydrate())
                .protein(foodRequestDto.getProtein())
                .fat(foodRequestDto.getFat())
                .sodium(foodRequestDto.getSodium())
                .cholesterol(foodRequestDto.getCholesterol())
                .diet(diet)
                .build();

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

