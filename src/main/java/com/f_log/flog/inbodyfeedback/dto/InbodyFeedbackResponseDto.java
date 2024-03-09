package com.f_log.flog.inbodyfeedback.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InbodyFeedbackResponseDto {
    private final UUID uuid;
    private final String inbodyFeedback;

    public InbodyFeedbackResponseDto(UUID uuid, String inbodyFeedback) {
        this.uuid = uuid;
        this.inbodyFeedback = inbodyFeedback;
    }
}
