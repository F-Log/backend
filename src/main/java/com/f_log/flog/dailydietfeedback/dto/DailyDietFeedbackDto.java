package com.f_log.flog.dailydietfeedback.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DailyDietFeedbackDto {
    private UUID dailyDietFeedbackUuid;
    private String content;
}
