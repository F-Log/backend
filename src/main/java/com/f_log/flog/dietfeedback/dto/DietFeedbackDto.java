package com.f_log.flog.dietfeedback.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DietFeedbackDto {
    private UUID dietFeedbackUuid;
    private String content;
}
