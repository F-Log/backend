package com.f_log.flog.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyIntakeDto {
    private double totalCarbohydrate;
    private double totalProtein;
    private double totalFat;
    private double totalSodium;
    private double totalCholesterol;
    private double totalSugars;
    private double totalCalories;
}