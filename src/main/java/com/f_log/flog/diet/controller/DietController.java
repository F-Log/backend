package com.f_log.flog.diet.controller;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.diet.dto.*;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;

import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietService;
    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<DietDto> createDiet(@RequestBody CreateDietRequest request) {
        DietDto dietDto = dietService.createDiet(request);
        return new ResponseEntity<>(dietDto, HttpStatus.CREATED);
    }

    @GetMapping("/date-memberUuid")
    public ResponseEntity<List<DietDetailDto>> getDietDetails(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("memberUuid") UUID memberUuid) {

        List<Diet> diets = dietService.findDietsByDateAndMemberUuid(date, memberUuid);

        List<DietDetailDto> dietDetails = diets.stream().map(diet -> {
            List<DietFoodResponseDto> dietFoodDtos = diet.getDietFoods().stream()
                    .map(dietFood -> DietFoodResponseDto.builder()
                            .dietfoodUuid(dietFood.getDietfoodUuid())
                            .dietUuid(dietFood.getDiet().getDietUuid())
                            .foodUuid(dietFood.getFood().getFoodUuid())
                            .quantity(dietFood.getQuantity())
                            .foodName(dietFood.getFoodName())
                            .notes(dietFood.getNotes())
                            .build())
                    .collect(Collectors.toList());

            // DietDetailDto를 빌드합니다.
            return DietDetailDto.builder()
                    .dietUuid(diet.getDietUuid())
                    .memberUuid(diet.getMember().getUuid())
                    .mealDate(diet.getMealDate())
                    .mealType(diet.getMealType())
                    .totalCalories(diet.getTotalCalories())
                    .totalCarbohydrate(diet.getTotalCarbohydrate())
                    .totalProtein(diet.getTotalProtein())
                    .totalFat(diet.getTotalFat())
                    .dietFoods(dietFoodDtos)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dietDetails);
    }


    @GetMapping("/{dietUuid}")
    public ResponseEntity<DietDto> getDiet(@PathVariable UUID dietUuid) {
        DietDto dietDto = dietService.getDietByUuid(dietUuid);
        return ResponseEntity.ok(dietDto);
    }

    @PutMapping("/{dietUuid}")
    public ResponseEntity<DietDto> updateDiet(@PathVariable UUID dietUuid, @RequestBody UpdateDietRequest request) {
        DietDto dietDto = dietService.updateDiet(dietUuid, request);
        return ResponseEntity.ok(dietDto);
    }

    @DeleteMapping("/{dietUuid}")
    public ResponseEntity<Void> deleteDiet(@PathVariable UUID dietUuid) {
        dietService.deleteDiet(dietUuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/daily-intake")
    public ResponseEntity<DailyIntakeDto> getTotalIntakeForDay(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                               @RequestParam("memberUuid") UUID memberUuid) {
        MemberResponseDto memberDto = memberService.getMemberByUuid(memberUuid);

        DailyIntakeDto dailyIntake = dietService.getTotalIntakeForDay(date, memberDto);
        return ResponseEntity.ok(dailyIntake);
    }

    @PostMapping("/register")
    public ResponseEntity<DietDto> registerDiet(@RequestBody RegisterDietRequest request) {
        DietDto dietDto = dietService.registerDiet(request);
        return new ResponseEntity<>(dietDto, HttpStatus.CREATED);
    }
}