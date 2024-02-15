package com.f_log.flog.dietfeedback.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietFeedbackDto {
    private Long id;
    private String dietFeedback;
}
