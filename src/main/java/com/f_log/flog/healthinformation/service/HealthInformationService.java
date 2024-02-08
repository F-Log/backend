package com.f_log.flog.healthinformation.service;

import com.f_log.flog.healthinformation.domain.HealthInformation;
import com.f_log.flog.healthinformation.repository.HealthInformationRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 멤버의 UUID를 사용하여 해당 멤버의 건강 정보를 조회합니다.
     *
     * @param uuid 멤버의 UUID
     * @return 조회된 건강 정보 또는 null
     */
    @Transactional
    public HealthInformation getHealthInformationByMemberUuid(UUID uuid) {
        return healthInformationRepository.findByMemberUuidAndIsDeletedFalse(uuid).orElse(null);
    }

    /**
     * 건강 정보를 저장합니다.
     *
     * @param healthInformation 건강 정보 엔티티
     * @param memberUuid 회원의 UUID
     * @return 저장된 건강 정보
     */
    @Transactional
    public HealthInformation saveHealthInformation(HealthInformation healthInformation, UUID memberUuid) {
        // 애플리케이션 수준에서 중복 체크
        Optional<HealthInformation> existingHealthInformation = healthInformationRepository.findByMemberUuidAndIsDeletedFalse(memberUuid);
        if (existingHealthInformation.isPresent()) {
            throw new IllegalArgumentException("Health information for member with UUID " + memberUuid + " already exists.");
        }

        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        healthInformation.setMember(member);
        return healthInformationRepository.save(healthInformation);
    }

    /**
     * 멤버의 UUID를 사용하여 해당 멤버의 건강 정보를 업데이트합니다.
     *
     * @param uuid 멤버의 UUID
     * @param updatedHealthInformation 업데이트할 건강 정보가 담긴 엔티티
     * @return 업데이트된 건강 정보 또는 null
     */
    @Transactional
    public HealthInformation updateHealthInformationByMemberUuid(UUID uuid, HealthInformation updatedHealthInformation) {
        HealthInformation healthInformation = healthInformationRepository.findByMemberUuidAndIsDeletedFalse(uuid).orElse(null);
        if (healthInformation != null) {
            healthInformation.changeDailyActivity(updatedHealthInformation.getDailyActivity());
            healthInformationRepository.save(healthInformation);
        }
        return healthInformation;
    }

    /**
     * 멤버의 UUID를 사용하여 해당 멤버의 건강 정보를 소프트 삭제합니다.
     *
     * @param uuid 멤버의 UUID
     * @return 삭제 성공 여부
     */
    @Transactional
    public boolean deleteHealthInformationByMemberUuid(UUID uuid) {
        return healthInformationRepository.findByMemberUuidAndIsDeletedFalse(uuid).map(healthInformation -> {
            healthInformation.setDeleted();
            healthInformationRepository.save(healthInformation);
            return true;
        }).orElse(false);
    }
}
