package com.f_log.flog.diet.service;

import com.f_log.flog.diet.controller.DietController;
import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.dto.CreateDietRequest;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.dto.DietMapper;
import com.f_log.flog.diet.dto.UpdateDietRequest;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.food.repository.FoodRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final DietMapper dietMapper;

    public DietDto findDietByUuid(UUID uuid) {
        Diet diet = dietRepository.findDietByUuid(uuid);
        return dietMapper.toDto(diet);
    }

    @Transactional
    public UUID saveDiet(CreateDietRequest createDietRequest) {
        UUID memberUuid = createDietRequest.getMemberUuid();
        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);

        UUID uuid = UUID.randomUUID();
        Diet diet = Diet.builder()
                .uuid(uuid)
                .member(member)
                .totalCarbohydrate(createDietRequest.getTotalCarbohydrate())
                .totalProtein(createDietRequest.getTotalProtein())
                .totalFat(createDietRequest.getTotalFat())
                .totalSodium(createDietRequest.getTotalSodium())
                .totalCholesterol(createDietRequest.getTotalCholesterol())
                .mealType(createDietRequest.getMealType())
                .mealDate(createDietRequest.getMealDate())
                .build();
        dietRepository.save(diet);
        return uuid;
    }

    @Transactional
    public boolean updateDiet(UUID uuid, UpdateDietRequest updateDietRequest) {
        Diet diet = dietRepository.findDietByUuid(uuid);
        if (diet != null) {
            diet.updateTotalCarbohydrate(updateDietRequest.getTotalCarbohydrate());
            diet.updateTotalSodium(updateDietRequest.getTotalSodium());
            diet.updateTotalProtein(updateDietRequest.getTotalProtein());
            diet.updateTotalFat(updateDietRequest.getTotalFat());
            diet.updateTotalCholesterol(updateDietRequest.getTotalCholesterol());
            diet.updateMealDate(updateDietRequest.getMealDate());
            diet.updateMealType(updateDietRequest.getMealType());
            dietRepository.save(diet);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteDiet(UUID uuid) {
        Diet diet = dietRepository.findDietByUuid(uuid);
        if (diet != null) {
            diet.setDeleted();
            dietRepository.save(diet);
            return true;
        } else {
            return false;
        }
    }

}
