package com.f_log.flog.allergy.service;

import com.f_log.flog.allergy.domain.Allergy;
import com.f_log.flog.allergy.dto.AllergyDto;
import com.f_log.flog.allergy.dto.AllergyMapper;
import com.f_log.flog.allergy.dto.AllergyRequest;
import com.f_log.flog.allergy.repository.AllergyRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AllergyService {
    private final AllergyRepository allergyRepository;
    private final MemberRepository memberRepository;
    private final AllergyMapper allergyMapper;

    @Transactional
    public AllergyDto createAllergy(UUID memberUuid, AllergyRequest allergyRequest) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        Allergy allergy = Allergy.builder()
                .member(member)
                .allergy(allergyRequest.getAllergy())
                .build();
        allergyRepository.save(allergy);
        return allergyMapper.toDto(allergy);
    }

    public AllergyDto findAllergy(UUID allergyUuid) {
        Allergy allergy = allergyRepository.findByUuidAndIsDeletedFalse(allergyUuid).orElse(null);
        return allergyMapper.toDto(allergy);
    }

    @Transactional
    public boolean updateAllergy(UUID allergyUuid, AllergyRequest allergyRequest) {
        Allergy allergy = allergyRepository.findByUuidAndIsDeletedFalse(allergyUuid).orElse(null);
        if (allergy != null) {
            allergy.setAllergy(allergyRequest.getAllergy());
            allergyRepository.save(allergy);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteAllergy(UUID allergyUuid) {
        Allergy allergy = allergyRepository.findByUuidAndIsDeletedFalse(allergyUuid).orElse(null);
        if (allergy != null) {
            allergy.setDeleted();
            allergyRepository.save(allergy);
            return true;
        } else {
            return false;
        }
    }

}
