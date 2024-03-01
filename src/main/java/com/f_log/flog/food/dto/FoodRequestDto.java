package com.f_log.flog.food.dto;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.food.domain.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FoodRequestDto {
    private final String foodName;
    private final int carbohydrate;
    private final int protein;
    private final int fat;
    private final int sodium;
    private final int cholesterol;
    private Long dietId;

    public Food toEntity(Diet diet) {
        return Food.builder()
                .foodName(foodName)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .sodium(sodium)
                .cholesterol(cholesterol)
                .diet(diet)
                .build();
    }
}
