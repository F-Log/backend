package com.f_log.flog.food.controller;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.FoodDto;
import com.f_log.flog.food.dto.FoodMapper;
import com.f_log.flog.food.dto.FoodRequestDto;
import com.f_log.flog.food.service.FoodService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final FoodMapper foodMapper;

    @PostMapping("/new")
    public ResponseEntity<FoodDto> createFood(@RequestBody @Valid FoodRequestDto foodRequestDto) {
        Food food = foodService.saveFood(foodRequestDto);
        FoodDto foodDto = foodMapper.toDto(food);
        return new ResponseEntity<>(foodDto, HttpStatus.CREATED);
    }


    @GetMapping("/{foodUuid}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable UUID foodUuid) {
        Food food = foodService.findFood(foodUuid);
        if (food == null) {
            return ResponseEntity.notFound().build();
        }
        FoodDto foodDto = foodMapper.toDto(food);
        return ResponseEntity.ok(foodDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FoodDto>> searchFoodByNameAndUser(
            @RequestParam String foodName,
            @RequestParam(required = false) UUID memberUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Food> foodPage = foodService.findFoodsByFoodNameAndUser(foodName, memberUuid, page, size);
        Page<FoodDto> foodDtoPage = foodPage.map(foodMapper::toDto);
        return ResponseEntity.ok(foodDtoPage);
    }

    @PutMapping("/{foodUuid}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable UUID foodUuid, @RequestBody FoodRequestDto foodRequestDto) {
        boolean updated = foodService.updateFood(foodUuid, foodRequestDto);
        if (updated) {
            Food food = foodService.findFood(foodUuid);
            FoodDto updatedFood = foodMapper.toDto(food);
            return ResponseEntity.ok(updatedFood);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{foodUuid}")
    public ResponseEntity<Void> deleteFood(@PathVariable UUID foodUuid) {
        boolean deleted = foodService.deleteFood(foodUuid);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}