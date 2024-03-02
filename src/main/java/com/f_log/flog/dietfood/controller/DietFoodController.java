package com.f_log.flog.dietfood.controller;

import com.f_log.flog.dietfood.dto.DietFoodRequestDTO;
import com.f_log.flog.dietfood.dto.DietFoodResponseDTO;
import com.f_log.flog.dietfood.service.DietFoodService;
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

    @GetMapping("/{id}")
    public ResponseEntity<DietFoodResponseDTO> getDietFood(@PathVariable Long id) {
        DietFoodResponseDTO responseDTO = dietFoodService.findDietFood(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietFoodResponseDTO> updateDietFood(@PathVariable Long id, @RequestBody DietFoodRequestDTO requestDTO) {
        DietFoodResponseDTO responseDTO = dietFoodService.updateDietFood(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietFood(@PathVariable Long id) {
        dietFoodService.deleteDietFood(id);
        return ResponseEntity.ok().build();
    }
}
