package com.f_log.flog.dietfood.dto;

import lombok.Data;

@Data
public class DietFoodRequestDTO {
    private Long dietId;
    private Long foodId;
    private int quantity;
    private String notes;
}
