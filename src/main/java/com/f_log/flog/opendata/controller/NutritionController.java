package com.f_log.flog.opendata.controller;

import com.f_log.flog.food.domain.Food;
import com.f_log.flog.food.service.FoodService;
import com.f_log.flog.opendata.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/api/v1/nutrition")
    public ResponseEntity<List<Food>> getNutritionInfo(
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "1000") String numOfRows,
            @RequestParam(defaultValue = "json") String type,
            @RequestParam(required = false) String foodName) {

        List<Food> foods = nutritionService.getNutritionInfo(pageNo, numOfRows, type, foodName);
        foodService.saveAllFoods(foods);

        if (foods != null) {
            foodService.saveAllFoods(foods);
            return ResponseEntity.ok().body(foods);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
