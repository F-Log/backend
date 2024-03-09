package com.f_log.flog.healthinformation.controller;

import com.f_log.flog.healthinformation.dto.HealthInformationMapper;
import com.f_log.flog.healthinformation.dto.HealthInformationRequestDto;
import com.f_log.flog.healthinformation.dto.HealthInformationResponseDto;
import com.f_log.flog.healthinformation.service.HealthInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/health-information")
public class HealthInformationController {

    private final HealthInformationService healthInformationService;
    private final HealthInformationMapper healthInformationMapper;

    public HealthInformationController(HealthInformationService healthInformationService, HealthInformationMapper healthInformationMapper) {
        this.healthInformationService = healthInformationService;
        this.healthInformationMapper = healthInformationMapper;
    }

    /**
     * 건강 정보를 생성합니다.
     *
     * @param requestDto 요청 DTO
     * @return 생성된 건강 정보 응답 DTO
     */
    @PostMapping("/new")
    public ResponseEntity<HealthInformationResponseDto> createHealthInformation(@RequestBody HealthInformationRequestDto requestDto) {
        HealthInformationResponseDto createdHealthInformation = healthInformationService.createHealthInformation(requestDto);
        return new ResponseEntity<>(createdHealthInformation, HttpStatus.CREATED);
    }

    /**
     * 멤버의 건강 정보를 조회합니다.
     *
     * @param memberUuid 멤버 UUID
     * @return 건강 정보 응답 DTO
     */
    @GetMapping("/{memberUuid}")
    public ResponseEntity<HealthInformationResponseDto> getHealthInformationByMemberUuid(@PathVariable UUID memberUuid) {
        return healthInformationService.getHealthInformationByMemberUuid(memberUuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 멤버의 건강 정보를 업데이트합니다.
     *
     * @param memberUuid       멤버 UUID
     * @param requestDto 요청 DTO
     * @return 업데이트된 건강 정보 응답 DTO
     */
    @PutMapping("/edit")
    public ResponseEntity<HealthInformationResponseDto> updateHealthInformationByMemberUuid(@RequestBody HealthInformationRequestDto requestDto) {
        HealthInformationResponseDto updatedHealthInformation = healthInformationService.updateHealthInformationByMemberUuid(requestDto);
        return ResponseEntity.ok(updatedHealthInformation);
    }

    /**
     * 멤버의 건강 정보를 삭제합니다.
     *
     * @param memberUuid 멤버 UUID
     * @return 삭제 결과 응답
     */
    @DeleteMapping("/{memberUuid}")
    public ResponseEntity<Void> deleteHealthInformationByMemberUuid(@PathVariable UUID memberUuid) {
        boolean deleted = healthInformationService.deleteHealthInformationByMemberUuid(memberUuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
