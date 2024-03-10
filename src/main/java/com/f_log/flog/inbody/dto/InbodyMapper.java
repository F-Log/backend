package com.f_log.flog.inbody.dto;

import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class InbodyMapper {

    public InbodyResponseDto toDto(Inbody inbody) {
        if (inbody == null) {
            return null;
        }
        return new InbodyResponseDto(
                inbody.getInbodyUuid(),
                inbody.getMember() != null ? inbody.getMember().getUuid() : null,
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
        if (requestDto == null || requestDto.getMemberUuid() == null) {
            return null;
        }
        // Create a new Inbody entity with the provided data
        Inbody inbody = new Inbody();
        inbody.setMember(new Member(requestDto.getMemberUuid())); // Assuming Member constructor accepts UUID
        inbody.setBodyWeight(requestDto.getBodyWeight());
        inbody.setHeight(requestDto.getHeight());
        inbody.setMuscleMass(requestDto.getMuscleMass());
        inbody.setBodyFatPercentage(requestDto.getBodyFatPercentage());
        inbody.setFatMass(requestDto.getFatMass());
        inbody.setBasalMetabolicRate(requestDto.getBasalMetabolicRate());
        return inbody;
    }
}
