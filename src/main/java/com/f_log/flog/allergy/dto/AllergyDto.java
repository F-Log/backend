package com.f_log.flog.allergy.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AllergyDto {
    private Long id;
    private String allergy;
}
