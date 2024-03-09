package com.f_log.flog.dietfood.controller;

import com.f_log.flog.dietfood.dto.DietFoodRequestDTO;
import com.f_log.flog.dietfood.dto.DietFoodResponseDTO;
import com.f_log.flog.dietfood.service.DietFoodService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dietfoods")
@RequiredArgsConstructor
public class DietFoodController {
    private final DietFoodService dietFoodService;

    @PostMapping("/new")
    public ResponseEntity<DietFoodResponseDTO> createDietFood(@RequestBody DietFoodRequestDTO requestDTO) {
        DietFoodResponseDTO responseDTO = dietFoodService.createDietFood(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{dietfoodUuid}")
    public ResponseEntity<DietFoodResponseDTO> getDietFood(@PathVariable UUID dietfoodUuid) {
        DietFoodResponseDTO responseDTO = dietFoodService.findDietFood(dietfoodUuid);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{dietfoodUuid}")
    public ResponseEntity<DietFoodResponseDTO> updateDietFood(@PathVariable UUID dietfoodUuid, @RequestBody DietFoodRequestDTO requestDTO) {
        DietFoodResponseDTO responseDTO = dietFoodService.updateDietFood(dietfoodUuid, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{dietfoodUuid}")
    public ResponseEntity<Void> deleteDietFood(@PathVariable UUID dietfoodUuid) {
        dietFoodService.deleteDietFood(dietfoodUuid);
        return ResponseEntity.ok().build();
    }
}
