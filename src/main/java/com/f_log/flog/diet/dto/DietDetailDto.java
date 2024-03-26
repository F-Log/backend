package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.MealType;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DietDetailDto {
    private UUID dietUuid;
    private UUID memberUuid;
    private LocalDate mealDate;
    private MealType mealType;
    private double totalCalories;
    private double totalCarbohydrate;
    private double totalProtein;
    private double totalFat;
    private List<DietFoodResponseDto> dietFoods;
}
