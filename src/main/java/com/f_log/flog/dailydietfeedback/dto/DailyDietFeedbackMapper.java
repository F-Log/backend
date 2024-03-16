package com.f_log.flog.dailydietfeedback.dto;

import com.f_log.flog.dailydietfeedback.domain.DailyDietFeedback;
import org.springframework.stereotype.Component;

@Component
public class DailyDietFeedbackMapper {

    public DailyDietFeedbackDto toDto(DailyDietFeedback dailyDietFeedback) {
        return DailyDietFeedbackDto.builder()
                .content(dailyDietFeedback.getDietFeedback())
                .dailyDietFeedbackUuid(dailyDietFeedback.getDietfeedbackUuid())
                .build();
    }

}