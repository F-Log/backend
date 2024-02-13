package com.f_log.flog.diet.dto;

import com.f_log.flog.domain.MealType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateDietRequest {
    private int totalCarbohydrate;
    private int totalProtein;
    private int totalFat;
    private int totalSodium;
    private int totalCholesterol;
    private MealType mealType;
    private LocalDateTime mealDate;
}
