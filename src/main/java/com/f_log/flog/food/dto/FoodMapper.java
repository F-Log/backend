package com.f_log.flog.food.dto;

import com.f_log.flog.food.domain.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodDto toDto(Food food) {
        return FoodDto.builder()
                .foodUuid(food.getFoodUuid())
                .foodName(food.getFoodName())
                .makerName(food.getMakerName())
                .carbohydrate(food.getCarbohydrate())
                .protein(food.getProtein())
                .fat(food.getFat())
                .sodium(food.getSodium())
                .cholesterol(food.getCholesterol())
                .sugars(food.getSugars())
                .calories(food.getCalories())
                .servingSize(food.getServingSize())
                .servingUnit(food.getServingUnit())
                .saturatedFat(food.getSaturatedFat())
                .transFat(food.getTransFat())
                .build();
    }

    public Food toEntity(FoodDto foodDto) {
        return Food.builder()
                .foodUuid(foodDto.getFoodUuid())
                .foodName(foodDto.getFoodName())
                .makerName(foodDto.getMakerName())
                .carbohydrate(foodDto.getCarbohydrate())
                .protein(foodDto.getProtein())
                .fat(foodDto.getFat())
                .sodium(foodDto.getSodium())
                .cholesterol(foodDto.getCholesterol())
                .sugars(foodDto.getSugars())
                .calories(foodDto.getCalories())
                .servingSize(foodDto.getServingSize())
                .servingUnit(foodDto.getServingUnit())
                .saturatedFat(foodDto.getSaturatedFat())
                .transFat(foodDto.getTransFat())
                .build();
    }
}
