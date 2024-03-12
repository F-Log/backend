package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.MealType;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DietDto {
    private UUID dietUuid;
    private UUID memberUuid;
    private int totalCarbohydrate;
    private int totalProtein;
    private int totalFat;
    private int totalSodium;
    private int totalCholesterol;
    private int totalSugars;
    private int totalCalories;
    private MealType mealType;
    private LocalDate mealDate;
    private List<DietFoodResponseDto> dietFoods;
}
