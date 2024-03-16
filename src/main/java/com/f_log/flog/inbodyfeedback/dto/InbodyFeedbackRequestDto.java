package com.f_log.flog.inbodyfeedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class InbodyFeedbackRequestDto {
    private UUID inbodyUuid;
    private String content;

    public InbodyFeedbackRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

