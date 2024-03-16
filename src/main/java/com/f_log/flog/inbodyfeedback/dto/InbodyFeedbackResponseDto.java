package com.f_log.flog.inbodyfeedback.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InbodyFeedbackResponseDto {
    private final UUID uuid;
    private final String content;

    public InbodyFeedbackResponseDto(UUID uuid, String content) {
        this.uuid = uuid;
        this.content = content;
    }
}
