package com.f_log.flog.food.service;

import com.f_log.flog.food.domain.Food;
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

    public List<Food> findAllFoods(){
        return foodRepository.findAll();
    }
}

