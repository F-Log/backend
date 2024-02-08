package com.f_log.flog.diet.service;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.repository.DietRepository;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.domain.MealType;
import com.f_log.flog.food.repository.FoodRepository;
import com.f_log.flog.member.domain.Member;
import com.f_log.flog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;

    public Diet findDietByUuid(UUID uuid) {
        return dietRepository.findDietByUuid(uuid);
    }

    @Transactional
    public UUID createDiet(UUID memberUuid, MealType mealType, LocalDateTime mealDate, Long foodId) {
        UUID uuid = UUID.randomUUID();
        Member member = memberRepository.findByUuidAndIsDeletedFalse(memberUuid);
        Food food = foodRepository.findByIdAndIsDeletedFalse(foodId).orElse(null);
        Diet diet = Diet.createDiet(member, mealType, mealDate, food);
        diet.setUuid(uuid);
        return uuid;
    }

}
