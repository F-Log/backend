package com.f_log.flog.diet.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.dto.*;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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

        Diet diet = Diet.builder()
                .member(member)
                .totalCarbohydrate(request.getTotalCarbohydrate())
                .totalProtein(request.getTotalProtein())
                .totalFat(request.getTotalFat())
                .totalSodium(request.getTotalSodium())
                .totalCholesterol(request.getTotalCholesterol())
                .totalSugars(request.getTotalSugars())
                .totalCalories(request.getTotalCalories())
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
            diet.updateTotalSugars(request.getTotalSugars());
            diet.updateTotalCalories(request.getTotalCalories());
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

    public DailyIntakeDto getTotalIntakeForDay(LocalDate date, MemberResponseDto memberDto) {
        List<Diet> diets = dietRepository.findByMealDateAndMemberUuidAndIsDeletedFalse(date, memberDto.getUuid());

        int totalCarbohydrate = 0;
        int totalProtein = 0;
        int totalFat = 0;
        int totalSodium = 0;
        int totalCholesterol = 0;
        int totalSugars = 0;
        int totalCalories = 0;

        for (Diet diet : diets) {
            totalCarbohydrate += diet.getTotalCarbohydrate();
            totalProtein += diet.getTotalProtein();
            totalFat += diet.getTotalFat();
            totalSodium += diet.getTotalSodium();
            totalCholesterol += diet.getTotalCholesterol();
            totalSugars += diet.getTotalSugars();
            totalCalories += diet.getTotalCalories();
        }

        DailyIntakeDto dailyIntake = DailyIntakeDto.builder()
                .totalCarbohydrate(totalCarbohydrate)
                .totalProtein(totalProtein)
                .totalFat(totalFat)
                .totalSodium(totalSodium)
                .totalCholesterol(totalCholesterol)
                .totalSugars(totalSugars)
                .totalCalories(totalCalories)
                .build();

        return dailyIntake;
    }
}