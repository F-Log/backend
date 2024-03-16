package com.f_log.flog.food.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "food_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID foodUuid;

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
