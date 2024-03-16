package com.f_log.flog.food.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FoodDto {
    private final UUID foodUuid;
    private final String foodName;
    private final String makerName;
    private final double carbohydrate;
    private final double protein;
    private final double fat;
    private final double sodium;
    private final double cholesterol;
    private final double sugars;
    private final int calories;
    private final double transFat;
    private final double saturatedFat;
    private int servingSize;
    private int servingUnit;


}
