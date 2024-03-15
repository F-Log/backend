package com.f_log.flog.dailydietfeedback.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DailyDietFeedbackDto {
    private UUID dailyDietFeedbackUuid;
    private String content;
}
