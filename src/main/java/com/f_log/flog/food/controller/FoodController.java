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


    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable Long id) {
        Food food = foodService.findFood(id);
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

    @PutMapping("/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable Long id, @RequestBody FoodRequestDto foodRequestDto) {
        boolean updated = foodService.updateFood(id, foodRequestDto);
        if (updated) {
            Food food = foodService.findFood(id);
            FoodDto updatedFood = foodMapper.toDto(food);
            return ResponseEntity.ok(updatedFood);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        boolean deleted = foodService.deleteFood(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}