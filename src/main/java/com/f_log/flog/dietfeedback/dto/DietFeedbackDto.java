package com.f_log.flog.dietfeedback.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DietFeedbackDto {
    private UUID dietfeedbackUuid;
    private String dietFeedback;
}
