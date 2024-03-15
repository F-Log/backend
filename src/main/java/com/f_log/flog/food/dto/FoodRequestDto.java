package com.f_log.flog.food.dto;

import com.f_log.flog.food.domain.Food;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FoodRequestDto {
    private final String foodName;
    private final String makerName;
    private final int servingSize;
    private final int servingUnit;
    private final double saturatedFat;
    private final double transFat;
    private final double carbohydrate;
    private final double protein;
    private final double fat;
    private final double sodium;
    private final double cholesterol;
    private final double sugars;
    private final int calories;
    private final UUID memberUuid;



    public Food toEntity() {
        return Food.builder()
                .foodName(foodName)
                .makerName(makerName)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .sodium(sodium)
                .cholesterol(cholesterol)
                .memberUuid(memberUuid)
                .sugars(sugars)
                .calories(calories)
                .servingSize(servingSize)
                .servingUnit(servingUnit)
                .saturatedFat(saturatedFat)
                .transFat(transFat)
                .build();
    }
}
