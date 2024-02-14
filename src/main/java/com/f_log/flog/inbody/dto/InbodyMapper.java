package com.f_log.flog.inbody.dto;

import com.f_log.flog.inbody.domain.Inbody;
import org.springframework.stereotype.Component;

@Component
public class InbodyMapper {

    public InbodyResponseDto toDto(Inbody inbody) {
        return new InbodyResponseDto(
                inbody.getInbodyUuid(),
                inbody.getMemberUuid(),
                inbody.getBodyWeight(),
                inbody.getHeight(),
                inbody.getMuscleMass(),
                inbody.getBodyFatPercentage(),
                inbody.getFatMass(),
                inbody.getBasalMetabolicRate(),
                inbody.getCreatedAt(),
                inbody.getUpdatedAt()
        );
    }

    public Inbody toEntity(InbodyRequestDto requestDto) {
        return new Inbody(
                requestDto.getMemberUuid(),
                requestDto.getBodyWeight(),
                requestDto.getHeight(),
                requestDto.getMuscleMass(),
                requestDto.getBodyFatPercentage(),
                requestDto.getFatMass(),
                requestDto.getBasalMetabolicRate()
        );
    }
}
