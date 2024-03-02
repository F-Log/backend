package com.f_log.flog.diet.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.dto.CreateDietRequest;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.dto.DietMapper;
import com.f_log.flog.diet.dto.UpdateDietRequest;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;
    private final DietMapper dietMapper;

    public DietDto getDietByUuid(UUID dietUuid) {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(dietUuid)
                .orElseThrow(() -> new EntityNotFoundException("Diet not found"));
        return dietMapper.toDto(diet);
    }

    @Transactional
    public DietDto createDiet(CreateDietRequest request) {
        Member member = memberRepository.findByUuidAndIsDeletedFalse(request.getMemberUuid());
        if (member == null) {
            throw new EntityNotFoundException("Member not found");
        }

        UUID dietUuid = UUID.randomUUID();

        Diet diet = Diet.builder()
                .dietUuid(dietUuid)
                .member(member)
                .totalCarbohydrate(request.getTotalCarbohydrate())
                .totalProtein(request.getTotalProtein())
                .totalFat(request.getTotalFat())
                .totalSodium(request.getTotalSodium())
                .totalCholesterol(request.getTotalCholesterol())
                .mealType(request.getMealType())
                .mealDate(request.getMealDate())
                .build();
        dietRepository.save(diet);
        return dietMapper.toDto(diet);
    }

    @Transactional
    public DietDto updateDiet(UUID dietUuid, UpdateDietRequest request) {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(dietUuid)
                .orElseThrow(() -> new EntityNotFoundException("Diet not found"));
        if (diet != null) {
            diet.updateTotalCarbohydrate(request.getTotalCarbohydrate());
            diet.updateTotalSodium(request.getTotalSodium());
            diet.updateTotalProtein(request.getTotalProtein());
            diet.updateTotalFat(request.getTotalFat());
            diet.updateTotalCholesterol(request.getTotalCholesterol());
            diet.updateMealDate(request.getMealDate());
            diet.updateMealType(request.getMealType());
        }
        dietRepository.save(diet);
        return dietMapper.toDto(diet);
    }

    @Transactional
    public void deleteDiet (UUID dietUuid) {
        Diet diet = dietRepository.findByDietUuidAndIsDeletedFalse(dietUuid)
                .orElseThrow(() -> new EntityNotFoundException("Diet not found"));
        diet.setDeleted();
        dietRepository.save(diet);
    }
}