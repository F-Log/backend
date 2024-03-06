package com.f_log.flog.food.service;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.FoodRequestDto;
import com.f_log.flog.food.repository.FoodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.util.StopWatch;

@Service
@RequiredArgsConstructor
public class FoodService {

    @PersistenceContext
    private EntityManager entityManager;

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

//    @Transactional
//    public void saveAllFoods(List<Food> foods) {
//
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        List<Food> filteredFoods = new ArrayList<>();
//        for (Food food : foods) {
//            List<Food> existingFood = foodRepository.findByFoodNameAndIsDeletedFalse(food.getFoodName());
//            if (existingFood.isEmpty()) {
//                filteredFoods.add(food);
//            }
//        }
//        if (!filteredFoods.isEmpty()) {
//            foodRepository.saveAll(filteredFoods);
//        }
//
//        stopWatch.stop();
//
//        System.out.println("Time taken to save foods: " + stopWatch.getTotalTimeMillis() + "ms");
//    }

    @Transactional
    public void saveFoodInBatches(List<Food> foods, int batchSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < foods.size(); i++) {
            if (foods.get(i).getId() == null) {
                entityManager.persist(foods.get(i));
            } else {
                entityManager.merge(foods.get(i));
            }
            if ((i + 1) % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        stopWatch.stop();
        System.out.println("Batch save time for Foods: " + stopWatch.getTotalTimeMillis() + "ms");
    }

    public List<Food> findAllFoods(){
        return foodRepository.findAll();
    }
}

