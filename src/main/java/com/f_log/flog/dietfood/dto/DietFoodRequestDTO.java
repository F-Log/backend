package com.f_log.flog.dietfood.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class DietFoodRequestDTO {
    private UUID dietUuid;
    private UUID foodUuid;
    private int quantity;
    private String notes;
}
