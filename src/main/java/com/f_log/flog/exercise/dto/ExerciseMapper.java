package com.f_log.flog.exercise.dto;

import com.f_log.flog.exercise.domain.Exercise;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ExerciseMapper {
    /**
     * 운동 정보 엔티티를 운동 정보 DTO로 변환합니다.
     * @param exercise 운동 정보 엔티티
     * @return 운동 정보 DTO
     */
    public ExerciseResponseDto toDto(Exercise exercise) {
        return ExerciseResponseDto.builder()
                .memberUuid(exercise.getMemberUuid())
                .exerciseType(exercise.getExerciseType())
                .targetWeight(exercise.getTargetWeight())
                .exerciseFrequency(exercise.getExerciseFrequency())
                .exerciseIntensity(exercise.getExerciseIntensity())
                .exercisePurpose(exercise.getExercisePurpose())
                .build();
    }

    /**
     * 요청 DTO와 회원 UUID를 바탕으로 운동 정보 엔티티를 생성합니다.
     * @param dto 요청 DTO
     * @param memberUuid 회원 UUID
     * @return 운동 정보 엔티티
     */
    public Exercise toEntity(ExerciseRequestDto dto, UUID memberUuid) {
        return new Exercise(memberUuid, dto.getExerciseType(), dto.getTargetWeight(), dto.getExerciseFrequency(),
                dto.getExerciseIntensity(), dto.getExercisePurpose());
    }

    /**
     * 요청 DTO와 회원 UUID를 바탕으로 운동 정보 엔티티를 생성합니다.
     * @param dto 요청 DTO
     * @param memberUuid 회원 UUID
     * @return 운동 정보 엔티티
     */
    public Exercise fromDto(ExerciseRequestDto dto, UUID memberUuid) {
        return new Exercise(memberUuid, dto.getExerciseType(), dto.getTargetWeight(), dto.getExerciseFrequency(),
                dto.getExerciseIntensity(), dto.getExercisePurpose());
    }
}
