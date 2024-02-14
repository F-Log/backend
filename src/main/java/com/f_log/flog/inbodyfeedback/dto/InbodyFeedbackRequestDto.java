package com.f_log.flog.inbodyfeedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InbodyFeedbackRequestDto {
    private String inbodyFeedback;

    public InbodyFeedbackRequestDto(String inbodyFeedback) {
        this.inbodyFeedback = inbodyFeedback;
    }

    public String getInbodyFeedback() {
        return inbodyFeedback;
    }

    public void setInbodyFeedback(String inbodyFeedback) {
        this.inbodyFeedback = inbodyFeedback;
    }
}

