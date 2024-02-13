package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.Diet;
import org.springframework.stereotype.Component;

@Component
public class DietMapper {
    public DietDto toDto(Diet diet) {
        return DietDto.builder()
                .uuid(diet.getUuid())
                .memberUuid(diet.getMember().getUuid())
                .totalCarbohydrate(diet.getTotalCarbohydrate())
                .totalProtein(diet.getTotalProtein())
                .totalFat(diet.getTotalFat())
                .totalSodium(diet.getTotalSodium())
                .totalCholesterol(diet.getTotalCholesterol())
                .mealDate(diet.getMealDate())
                .mealType(diet.getMealType())
                .build();
    }
}
