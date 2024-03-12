package com.f_log.flog.diet.controller;

import com.f_log.flog.diet.dto.*;
import com.f_log.flog.diet.service.DietService;
import com.f_log.flog.member.dto.MemberResponseDto;
import com.f_log.flog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
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