package com.f_log.flog.inbody.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class InbodyRequestDto {
    private final UUID memberUuid;
    private final float bodyWeight;
    private final float height;
    private final float muscleMass;
    private final float bodyFatPercentage;
    private final float fatMass;
    private final float basalMetabolicRate;

    public InbodyRequestDto(UUID memberUuid, float bodyWeight, float height, float muscleMass, float bodyFatPercentage, float fatMass, float basalMetabolicRate) {
        this.memberUuid = memberUuid;
        this.bodyWeight = bodyWeight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.bodyFatPercentage = bodyFatPercentage;
        this.fatMass = fatMass;
        this.basalMetabolicRate = basalMetabolicRate;
    }
}