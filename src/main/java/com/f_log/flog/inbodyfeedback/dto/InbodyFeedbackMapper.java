package com.f_log.flog.inbodyfeedback.dto;

import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.inbodyfeedback.domain.InbodyFeedback;
import org.springframework.stereotype.Component;

@Component
public class InbodyFeedbackMapper {

    public InbodyFeedbackResponseDto toDto(InbodyFeedback inbodyFeedback) {
        return new InbodyFeedbackResponseDto(
                inbodyFeedback.getUuid(),
                inbodyFeedback.getInbodyFeedback()
        );
    }

    public InbodyFeedback toEntity(InbodyFeedbackRequestDto requestDto, Inbody inbody) {
        return new InbodyFeedback(requestDto.getContent(), inbody);
    }
}
