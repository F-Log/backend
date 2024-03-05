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

    private int carbohydrate;
    private int protein;
    private int fat;
    private int sodium;
    private int cholesterol;
    private int sugars;
    private int calories;

    @Column(name = "member_uuid")
    private UUID memberUuid;

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
