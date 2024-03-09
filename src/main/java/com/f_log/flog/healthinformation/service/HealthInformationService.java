package com.f_log.flog.healthinformation.service;

import com.f_log.flog.healthinformation.domain.HealthInformation;
import com.f_log.flog.healthinformation.dto.HealthInformationRequestDto;
import com.f_log.flog.healthinformation.dto.HealthInformationResponseDto;
import com.f_log.flog.healthinformation.repository.HealthInformationRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HealthInformationService {
    private final HealthInformationRepository healthInformationRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public HealthInformationService(HealthInformationRepository healthInformationRepository, MemberRepository memberRepository) {
        this.healthInformationRepository = healthInformationRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 건강 정보를 생성합니다.
     *
     * @param memberUuid  회원 UUID
     * @param requestDto  요청 DTO
     * @return 생성된 건강 정보 DTO
     */
    @Transactional
    public HealthInformationResponseDto createHealthInformation(HealthInformationRequestDto requestDto) {
        UUID memberUuid = requestDto.getMemberUuid();
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid);
        if (existingHealthInformation.isPresent()) {
            throw new IllegalArgumentException("Health information for member with UUID " + memberUuid + " already exists.");
        }

        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        HealthInformation healthInformation = new HealthInformation();
        healthInformation.setMember(member);
        healthInformation.changeDailyActivity(requestDto.getDailyActivity());
        HealthInformation savedHealthInformation = healthInformationRepository.save(healthInformation);

        return HealthInformationResponseDto.fromEntity(savedHealthInformation);
    }

    /**
     * 멤버의 건강 정보를 조회합니다.
     *
     * @param memberUuid 멤버 UUID
     * @return 건강 정보 DTO
     */
    @Transactional
    public Optional<HealthInformationResponseDto> getHealthInformationByMemberUuid(UUID memberUuid) {
        return healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .map(HealthInformationResponseDto::fromEntity);
    }

    /**
     * 멤버의 건강 정보를 업데이트합니다.
     *
     * @param requestDto 요청 DTO
     * @return 업데이트된 건강 정보 DTO
     */
    @Transactional
    public HealthInformationResponseDto updateHealthInformationByMemberUuid(HealthInformationRequestDto requestDto) {
        UUID memberUuid = requestDto.getMemberUuid();
        HealthInformation healthInformation = healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .orElseThrow(() -> new IllegalArgumentException("Health information for member with UUID " + memberUuid + " not found."));

        healthInformation.changeDailyActivity(requestDto.getDailyActivity());
        HealthInformation updatedHealthInformation = healthInformationRepository.save(healthInformation);

        return HealthInformationResponseDto.fromEntity(updatedHealthInformation);
    }

    /**
     * 멤버의 건강 정보를 삭제합니다.
     *
     * @param memberUuid 멤버 UUID
     * @return 삭제 여부
     */
    @Transactional
    public boolean deleteHealthInformationByMemberUuid(UUID memberUuid) {
        return healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .map(healthInformation -> {
                    healthInformation.setDeleted();
                    healthInformationRepository.save(healthInformation);
                    return true;
                }).orElse(false);
    }
}
