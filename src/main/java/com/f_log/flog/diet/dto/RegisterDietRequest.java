package com.f_log.flog.diet.dto;

import com.f_log.flog.diet.domain.MealType;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RegisterDietRequest {
    private UUID memberUuid;
    private MealType mealType;
    private LocalDate mealDate;
}