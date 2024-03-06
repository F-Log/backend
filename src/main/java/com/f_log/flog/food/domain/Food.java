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
    private int saturatedFat; // 포화 지방
    private int transFat; // 트랜스 지방
    private int carbohydrate;
    private int protein;
    private int fat;
    private int sodium;
    private int cholesterol;
    private int sugars;
    private int calories;

    @Column(name = "member_uuid")
    private UUID memberUuid;

    public void updateServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void updateServingUnit(int servingUnit) {
        this.servingUnit = servingUnit;
    }

    public void updateSaturatedFat(int saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void updateTransFat(int transFat) {
        this.transFat = transFat;
    }

    public void updateCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void updateProtein(int protein) {
        this.protein = protein;
    }

    public void updateFat(int fat) {
        this.fat = fat;
    }

    public void updateSodium(int sodium) {
        this.sodium = sodium;
    }

    public void updateCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void updateSugars(int sugars) {
        this.sugars = sugars;
    }

    public void updateCalories(int calories) {
        this.calories = calories;
    }

    public void updateFoodName(String foodName) {
        this.foodName = foodName;
    }
}
