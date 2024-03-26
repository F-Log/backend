package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DietMapper {

    public DietDto toDto(Diet diet) {
        List<DietFoodResponseDto> dietFoodDtos = diet.getDietFoods().stream()
                .map(dietFood -> DietFoodResponseDto.builder()
                        .dietfoodUuid(dietFood.getDietfoodUuid())
                        .dietUuid(dietFood.getDiet().getDietUuid())
                        .foodUuid(dietFood.getFood().getFoodUuid())
                        .quantity(dietFood.getQuantity())
                        .foodName(dietFood.getFoodName())
                        .notes(dietFood.getNotes())
                        .build())
                .collect(Collectors.toList());

        return DietDto.builder()
                .dietUuid(diet.getDietUuid())
                .memberUuid(diet.getMember().getUuid())
                .totalCarbohydrate(diet.getTotalCarbohydrate())
                .totalProtein(diet.getTotalProtein())
                .totalFat(diet.getTotalFat())
                .totalSodium(diet.getTotalSodium())
                .totalCholesterol(diet.getTotalCholesterol())
                .totalSugars(diet.getTotalSugars())
                .totalCalories(diet.getTotalCalories())
                .mealDate(diet.getMealDate())
                .mealType(diet.getMealType())
                .dietFoods(dietFoodDtos)
                .build();
    }
}

