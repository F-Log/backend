package com.f_log.flog.dietfood.dto;

import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Getter
public class CreateDietFoodRequest {
    private UUID memberUuid;
    private UUID foodUuid;
    private UUID dietUuid;
    private float quantity;
    private String notes;
}
