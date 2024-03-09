package com.f_log.flog.allergy.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AllergyDto {
    private UUID uuid;
    private String allergy;
}
