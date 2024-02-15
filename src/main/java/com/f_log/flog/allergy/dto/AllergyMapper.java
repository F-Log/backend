package com.f_log.flog.allergy.dto;

import com.f_log.flog.allergy.domain.Allergy;
import org.springframework.stereotype.Component;

@Component
public class AllergyMapper {

    public AllergyDto toDto(Allergy allergy) {
        return AllergyDto.builder()
                .id(allergy.getId())
                .allergy(allergy.getAllergy())
                .build();
    }


}
