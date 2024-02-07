package com.f_log.flog.service;

import com.f_log.flog.domain.Diet;
import com.f_log.flog.domain.Food;
import com.f_log.flog.domain.Member;
import com.f_log.flog.repository.DietRepository;
import com.f_log.flog.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;
    private final MemberRepository memberRepository;

    public Optional<Diet> findDietById(Long dietId) {
        return dietRepository.findById(dietId);
    }

    /* TODO:
    *   1. 멤버 찾기
    *   2. foods로부터 영양정보 총합 구하기
    *   3. 멤버의 diet정보로 저장
    */
//    @Transactional
//    public Diet createDiet(Long memberId, Diet diet, List<Food> foods) {
//
//    }

    /*
    TODO: Food로부터 영양정보 총합 구하는 메소드
     */

}
