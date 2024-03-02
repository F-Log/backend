package com.f_log.flog.dietfood.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietFoodResponseDTO {
    private Long id;
    private Long dietId;
    private Long foodId;
    private int quantity;
    private String notes;
}
