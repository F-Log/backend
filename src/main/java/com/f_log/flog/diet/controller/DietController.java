package com.f_log.flog.diet.controller;

import com.f_log.flog.diet.dto.CreateDietRequest;
import com.f_log.flog.diet.dto.DietDto;
import com.f_log.flog.diet.dto.UpdateDietRequest;
import com.f_log.flog.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietService;

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
}