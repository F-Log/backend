package com.f_log.flog.dietfood.controller;

import com.f_log.flog.dietfood.dto.CreateDietFoodRequest;
import com.f_log.flog.dietfood.dto.DietFoodResponseDto;
import com.f_log.flog.dietfood.dto.UpdateDietFoodRequest;
import com.f_log.flog.dietfood.service.DietFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dietfoods")
@RequiredArgsConstructor
public class DietFoodController {
    private final DietFoodService dietFoodService;

    @PostMapping("/new")
    public ResponseEntity<DietFoodResponseDto> createDietFood(@RequestBody CreateDietFoodRequest createDietFoodRequest) {
        DietFoodResponseDto responseDTO = dietFoodService.createDietFood(createDietFoodRequest);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{dietfoodUuid}")
    public ResponseEntity<DietFoodResponseDto> getDietFood(@PathVariable UUID dietfoodUuid) {
        DietFoodResponseDto responseDTO = dietFoodService.findDietFood(dietfoodUuid);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{dietfoodUuid}")
    public ResponseEntity<DietFoodResponseDto> updateDietFood(@PathVariable UUID dietfoodUuid, @RequestBody UpdateDietFoodRequest updateDietFoodRequest) {
        DietFoodResponseDto responseDTO = dietFoodService.updateDietFood(dietfoodUuid, updateDietFoodRequest);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{dietfoodUuid}")
    public ResponseEntity<Void> deleteDietFood(@PathVariable UUID dietfoodUuid) {
        dietFoodService.deleteDietFood(dietfoodUuid);
        return ResponseEntity.ok().build();
    }
}
