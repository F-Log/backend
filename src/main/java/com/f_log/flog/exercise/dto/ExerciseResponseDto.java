package com.f_log.flog.exercise.dto;

import com.f_log.flog.exercise.domain.ExerciseIntensity;
import com.f_log.flog.exercise.domain.ExercisePurpose;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ExerciseResponseDto {
    private UUID memberUuid;
    private String exerciseType;
    private float targetWeight;
    private float exerciseFrequency;
    private ExerciseIntensity exerciseIntensity;
    private ExercisePurpose exercisePurpose;
}
