package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.MealType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UpdateDietRequest {
    private double totalCarbohydrate;
    private double totalProtein;
    private double totalFat;
    private double totalSodium;
    private double totalCholesterol;
    private double totalSugars;
    private double totalCalories;
    private MealType mealType;
    private LocalDate mealDate;
}
