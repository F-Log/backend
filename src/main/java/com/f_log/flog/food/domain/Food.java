package com.f_log.flog.food.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    private String foodName;

    private int servingSize; // 1회 제공량
    private int servingUnit; // 총 내용량
    private double saturatedFat; // 포화 지방
    private double transFat; // 트랜스 지방
    private double carbohydrate;
    private double protein;
    private double fat;
    private double sodium;
    private double cholesterol;
    private double sugars;
    private int calories;
    private String makerName; // 제조사명


    @Column(name = "member_uuid")
    private UUID memberUuid;

    public void updateMakerName(String makerName) {
        this.makerName = makerName;
    }

    public void updateServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void updateServingUnit(int servingUnit) {
        this.servingUnit = servingUnit;
    }

    public void updateSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void updateTransFat(double transFat) {
        this.transFat = transFat;
    }

    public void updateCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void updateProtein(double protein) {
        this.protein = protein;
    }

    public void updateFat(double fat) {
        this.fat = fat;
    }

    public void updateSodium(double sodium) {
        this.sodium = sodium;
    }

    public void updateCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void updateSugars(double sugars) {
        this.sugars = sugars;
    }

    public void updateCalories(int calories) {
        this.calories = calories;
    }

    public void updateFoodName(String foodName) {
        this.foodName = foodName;
    }
}
