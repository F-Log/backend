package com.f_log.flog.food.domain;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    private Diet diet;

    private String foodName;

    private int carbohydrate;
    private int protein;
    private int fat;
    private int sodium;
    private int cholesterol;

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

    public void updateFoodName(String foodName) {
        this.foodName = foodName;
    }
}
