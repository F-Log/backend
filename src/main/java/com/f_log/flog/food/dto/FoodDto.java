package com.f_log.flog.food.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodDto {
    private final Long id;
    private final String foodName;
    private final int carbohydrate;
    private final int protein;
    private final int fat;
    private final int sodium;
    private final int cholesterol;
    private final int sugars;
    private final int calories;
}
