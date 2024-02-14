package com.f_log.flog.exercise.service;

import com.f_log.flog.exercise.domain.Exercise;
import com.f_log.flog.exercise.dto.ExerciseMapper;
import com.f_log.flog.exercise.dto.ExerciseRequestDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.exercise.repository.ExerciseRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MemberRepository memberRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, MemberRepository memberRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.memberRepository = memberRepository;
        this.exerciseMapper = exerciseMapper;
    }

    /**
     * 새로운 운동 정보를 생성합니다.
     * @param memberUuid 회원의 UUID
     * @param newExerciseDto 요청 DTO
     * @return 생성된 운동 정보 DTO
     * @throws IllegalArgumentException 회원을 찾을 수 없거나 이미 운동 정보가 존재할 경우 발생
     */
    @Transactional
    public ExerciseResponseDto createExercise(UUID memberUuid, ExerciseRequestDto newExerciseDto) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        if (member == null) {
            throw new IllegalArgumentException("Member not found.");
        }
        if (exerciseRepository.findByMemberUuidAndIsDeletedFalse(memberUuid).isPresent()) {
            throw new IllegalStateException("Exercise already exists for this member.");
        }
        Exercise newExercise = exerciseMapper.toEntity(newExerciseDto, memberUuid);
        Exercise savedExercise = exerciseRepository.save(newExercise);
        return exerciseMapper.toDto(savedExercise);
    }

    /**
     * 운동 정보를 업데이트합니다.
     * @param memberUuid 회원의 UUID
     * @param updatedExerciseDto 업데이트할 운동 정보 DTO
     * @return 업데이트된 운동 정보 DTO
     * @throws IllegalArgumentException 해당 회원의 운동 정보를 찾을 수 없을 경우 발생
     */
    @Transactional
    public ExerciseResponseDto updateExercise(UUID memberUuid, ExerciseRequestDto updatedExerciseDto) {
        Exercise exercise = exerciseRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found for this member."));
        exercise.updateExercise(updatedExerciseDto.getExerciseType(), updatedExerciseDto.getTargetWeight(),
                updatedExerciseDto.getExerciseFrequency(), updatedExerciseDto.getExerciseIntensity(),
                updatedExerciseDto.getExercisePurpose());
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(updatedExercise);
    }

    /**
     * 특정 회원의 운동 정보를 조회합니다.
     * @param memberUuid 회원의 UUID
     * @return 운동 정보 DTO
     */
    public Optional<ExerciseResponseDto> getExercise(UUID memberUuid) {
        return exerciseRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .map(exerciseMapper::toDto);
    }

    /**
     * 특정 회원의 운동 정보를 삭제합니다.
     * @param memberUuid 회원의 UUID
     * @throws IllegalArgumentException 해당 회원의 운동 정보를 찾을 수 없을 경우 발생
     */
    @Transactional
    public void deleteExercise(UUID memberUuid) {
        Exercise exercise = exerciseRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found for this member."));
        exercise.setDeleted();
        exerciseRepository.save(exercise);
    }
}
