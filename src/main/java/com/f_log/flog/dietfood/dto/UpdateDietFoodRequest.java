package com.f_log.flog.dietfood.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateDietFoodRequest {
    private float quantity;
    private String notes;
}
