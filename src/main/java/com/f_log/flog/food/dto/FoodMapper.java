package com.f_log.flog.food.dto;

import com.f_log.flog.food.domain.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodDto toDto(Food food) {
        return FoodDto.builder()
                .id(food.getId())
                .foodName(food.getFoodName())
                .carbohydrate(food.getCarbohydrate())
                .protein(food.getProtein())
                .fat(food.getFat())
                .sodium(food.getSodium())
                .cholesterol(food.getCholesterol())
                .sugars(food.getSugars())
                .calories(food.getCalories())
                .build();
    }

    public Food toEntity(FoodDto foodDto) {
        return Food.builder()
                .id(foodDto.getId())
                .foodName(foodDto.getFoodName())
                .carbohydrate(foodDto.getCarbohydrate())
                .protein(foodDto.getProtein())
                .fat(foodDto.getFat())
                .sodium(foodDto.getSodium())
                .cholesterol(foodDto.getCholesterol())
                .sugars(foodDto.getSugars())
                .calories(foodDto.getCalories())
                .build();
    }
}
