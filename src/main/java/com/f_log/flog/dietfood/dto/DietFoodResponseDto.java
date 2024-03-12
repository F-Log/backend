package com.f_log.flog.dietfood.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietFoodResponseDto {
    private UUID dietfoodUuid;
    private UUID dietUuid;
    private UUID foodUuid;
    private float quantity;
    private String foodName;
    private String notes;
}
