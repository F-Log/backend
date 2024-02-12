package com.f_log.flog.food.controller;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.dto.CreateFoodRequest;
import com.f_log.flog.food.dto.FoodDto;
import com.f_log.flog.food.dto.FoodMapper;
import com.f_log.flog.food.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<FoodDto> createFood(@RequestBody @Valid CreateFoodRequest createFoodRequest) {
        Food food = createFoodRequest.toEntity();
        foodService.saveFood(food);
        FoodDto foodDto = foodMapper.toDto(food);
        return new ResponseEntity<>(foodDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> getFoodById(@PathVariable Long id) {
        FoodDto foodDto = foodMapper.toDto(foodService.findFood(id));
        return ResponseEntity.ok(foodDto);
    }

}