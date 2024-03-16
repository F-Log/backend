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
            @RequestParam(defaultValue = "json") String type,
            @RequestParam(required = false) String foodName) {

        // pageNo와 numOfRows 파라미터를 제거합니다.
        List<Food> foods = nutritionService.getNutritionInfo(type, foodName);

        // 데이터가 있을 경우에만 배치 처리로 저장합니다.
        if (foods != null && !foods.isEmpty()) {
            int batchSize = 50; // 배치 사이즈를 설정합니다.
            foodService.saveFoodInBatches(foods, batchSize);
            return ResponseEntity.ok().body(foods);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

//    @GetMapping("/api/v1/nutrition")
//    public ResponseEntity<List<Food>> getNutritionInfo(
//            @RequestParam(defaultValue = "1") String pageNo,
//            @RequestParam(defaultValue = "1000") String numOfRows,
//            @RequestParam(defaultValue = "json") String type,
//            @RequestParam(required = false) String foodName) {
//
//        List<Food> foods = nutritionService.getNutritionInfo(pageNo, numOfRows, type, foodName);
//        foodService.saveAllFoods(foods);
//
//        if (foods != null) {
//            int batchSize = 100;
//            foodService.saveFoodInBatches(foods, batchSize);
//        }
//        return foods != null && !foods.isEmpty() ? ResponseEntity.ok().body(foods) : ResponseEntity.noContent().build();
//    }
}


//            foodService.saveAllFoods(foods);
//            return ResponseEntity.ok().body(foods);
//        } else {
//            return ResponseEntity.noContent().build();
