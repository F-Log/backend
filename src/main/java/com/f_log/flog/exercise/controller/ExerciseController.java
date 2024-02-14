package com.f_log.flog.exercise.controller;

import com.f_log.flog.exercise.dto.ExerciseRequestDto;
import com.f_log.flog.exercise.dto.ExerciseResponseDto;
import com.f_log.flog.exercise.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /**
     * 새로운 운동 정보를 생성합니다.
     * @param memberUuid 회원의 UUID
     * @param requestDto 요청 DTO
     * @return 생성된 운동 정보 DTO와 함께 생성됨을 나타내는 상태 코드
     */
    @PostMapping("/{memberUuid}")
    public ResponseEntity<ExerciseResponseDto> createExercise(@PathVariable UUID memberUuid, @RequestBody ExerciseRequestDto requestDto) {
        ExerciseResponseDto createdExercise = exerciseService.createExercise(memberUuid, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }

    /**
     * 특정 회원의 운동 정보를 조회합니다.
     * @param memberUuid 조회할 회원의 UUID
     * @return 조회된 운동 정보 DTO와 함께 조회됨을 나타내는 상태 코드
     */
    @GetMapping("/{memberUuid}")
    public ResponseEntity<ExerciseResponseDto> getExercise(@PathVariable UUID memberUuid) {
        return exerciseService.getExercise(memberUuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 기존의 운동 정보를 업데이트합니다.
     * @param memberUuid 운동 정보를 업데이트할 회원의 UUID
     * @param requestDto 요청 DTO
     * @return 업데이트된 운동 정보 DTO와 함께 업데이트됨을 나타내는 상태 코드
     */
    @PutMapping("/{memberUuid}")
    public ResponseEntity<ExerciseResponseDto> updateExercise(@PathVariable UUID memberUuid, @RequestBody ExerciseRequestDto requestDto) {
        ExerciseResponseDto updatedExercise = exerciseService.updateExercise(memberUuid, requestDto);
        return ResponseEntity.ok(updatedExercise);
    }

    /**
     * 특정 회원의 운동 정보를 삭제합니다.
     * @param memberUuid 삭제할 회원의 UUID
     * @return 삭제된 운동 정보와 함께 삭제됨을 나타내는 상태 코드
     */
    @DeleteMapping("/{memberUuid}")
    public ResponseEntity<?> deleteExercise(@PathVariable UUID memberUuid) {
        exerciseService.deleteExercise(memberUuid);
        return ResponseEntity.ok().build();
    }
}
