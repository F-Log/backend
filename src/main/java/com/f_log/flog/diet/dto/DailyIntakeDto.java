package com.f_log.flog.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyIntakeDto {
    private int totalCarbohydrate;
    private int totalProtein;
    private int totalFat;
    private int totalSodium;
    private int totalCholesterol;
    private int totalSugars;
    private int totalCalories;
}