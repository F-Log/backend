package com.f_log.flog.inbody.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class InbodyResponseDto {
    private final UUID inbodyUuid;
    private final UUID memberUuid;
    private final float bodyWeight;
    private final float height;
    private final float muscleMass;
    private final float bodyFatPercentage;
    private final float fatMass;
    private final float basalMetabolicRate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public InbodyResponseDto(UUID inbodyUuid, UUID memberUuid, float bodyWeight, float height, float muscleMass, float bodyFatPercentage, float fatMass, float basalMetabolicRate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.inbodyUuid = inbodyUuid;
        this.memberUuid = memberUuid;
        this.bodyWeight = bodyWeight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.bodyFatPercentage = bodyFatPercentage;
        this.fatMass = fatMass;
        this.basalMetabolicRate = basalMetabolicRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
