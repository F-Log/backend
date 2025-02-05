package com.f_log.flog.csvbatch.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class FoodRawDTO {

    private final String foodName;
    private final String calories;
    private final String protein;
    private final String fat;
    private final String carbohydrate;
    private final String sugars;
    private final String sodium;
    private final String cholesterol;
    private final String saturatedFat;
    private final String transFat;
    private final String servingUnit;
    private final String makerName;
}
