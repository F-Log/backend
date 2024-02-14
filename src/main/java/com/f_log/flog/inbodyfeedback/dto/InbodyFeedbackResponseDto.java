package com.f_log.flog.inbodyfeedback.dto;

import lombok.Getter;

@Getter
public class InbodyFeedbackResponseDto {
    private final Long id;
    private final String inbodyFeedback;

    public InbodyFeedbackResponseDto(Long id, String inbodyFeedback) {
        this.id = id;
        this.inbodyFeedback = inbodyFeedback;
    }
}
