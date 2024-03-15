package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.MealType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateDietRequest {
    private int totalCarbohydrate;
    private int totalProtein;
    private int totalFat;
    private int totalSodium;
    private int totalCholesterol;
    private int totalSugars;
    private int totalCalories;
    private MealType mealType;
    private LocalDate mealDate;
}
