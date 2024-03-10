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

    @Transactional
    public HealthInformationResponseDto createHealthInformation(HealthInformationRequestDto requestDto) {
        UUID memberUuid = requestDto.getMemberUuid();
        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        if (member == null) {
            throw new IllegalArgumentException("Member not found.");
        }

        HealthInformation healthInformation = new HealthInformation();
        healthInformation.setMember(member);
        healthInformation.changeDailyActivity(requestDto.getDailyActivity());
        HealthInformation savedHealthInformation = healthInformationRepository.save(healthInformation);

        // Update Member's healthInformation field to reference the newly created HealthInformation entity
        member.setHealthInformation(savedHealthInformation);
        memberRepository.save(member);

        return HealthInformationResponseDto.fromEntity(savedHealthInformation);
    }

    @Transactional
    public Optional<HealthInformationResponseDto> getHealthInformationByMemberUuid(UUID memberUuid) {
        return healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .map(HealthInformationResponseDto::fromEntity);
    }

    @Transactional
    public HealthInformationResponseDto updateHealthInformationByMemberUuid(HealthInformationRequestDto requestDto) {
        UUID memberUuid = requestDto.getMemberUuid();
        HealthInformation healthInformation = healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid)
                .orElseThrow(() -> new IllegalArgumentException("Health information for member with UUID " + memberUuid + " not found."));

        healthInformation.changeDailyActivity(requestDto.getDailyActivity());
        HealthInformation updatedHealthInformation = healthInformationRepository.save(healthInformation);

        return HealthInformationResponseDto.fromEntity(updatedHealthInformation);
    }

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
