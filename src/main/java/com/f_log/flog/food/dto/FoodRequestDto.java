package com.f_log.flog.food.dto;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.food.domain.Food;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodRequestDto {
    private final String foodName;
    private final int servingSize;
    private final int servingUnit;
    private final int saturatedFat;
    private final int transFat;
    private final int carbohydrate;
    private final int protein;
    private final int fat;
    private final int sodium;
    private final int cholesterol;
    private final int sugars;
    private final int calories;
    private final UUID memberUuid;

    public Food toEntity() {
        return Food.builder()
                .foodName(foodName)
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
