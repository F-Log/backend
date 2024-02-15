package com.f_log.flog.dietfeedback.dto;

import com.f_log.flog.dietfeedback.domain.DietFeedback;
import org.springframework.stereotype.Component;

@Component
public class DietFeedbackMapper {

    public DietFeedbackDto toDto(DietFeedback dietFeedback) {
        return DietFeedbackDto.builder()
                .dietFeedback(dietFeedback.getDietFeedback())
                .id(dietFeedback.getId())
                .build();
    }

}