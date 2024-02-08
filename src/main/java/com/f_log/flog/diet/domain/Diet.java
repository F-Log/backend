package com.f_log.flog.diet.domain;

import com.f_log.flog.domain.DietFeedback;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.domain.MealType;
import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "diet_id")
    private Long id;

    /* TODO: s3 url 추가 */

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

    @Column(name = "total_carbohydrate")
    private int totalCarbohydrate;

    @Column(name = "total_protein")
    private int totalProtein;

    @Column(name = "total_fat")
    private int totalFat;

    @Column(name = "total_sodium")
    private int totalSodium;

    @Column(name = "total_cholesterol")
    private int totalCholesterol;

    @Column(name = "meal_date")
    private LocalDateTime mealDate;

    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @OneToOne(mappedBy = "diet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DietFeedback dietFeedback;

    // member와의 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // constructor method
    public static Diet createDiet(Member member,
                                  MealType mealType,
                                  LocalDateTime mealDate,
                                  Food... foods) {
        Diet diet = new Diet();
        diet.member = member;
        diet.mealType = mealType;
        diet.mealDate = mealDate;
        for (Food food : foods) {
            diet.addFoods(food);
        }

        diet.totalCarbohydrate = diet.getTotalCarbohydrate();
        diet.totalProtein = diet.getTotalProtein();
        diet.totalFat = diet.getTotalFat();
        diet.totalSodium = diet.getTotalSodium();
        diet.totalCholesterol = diet.getTotalCholesterol();

        return diet;
    }

    private void addFoods(Food food) {
        foods.add(food);
    }

    private int getTotalCarbohydrate(){
        int totalValue = 0;
        for (Food food : foods) {
            totalValue += food.getCarbohydrate();
        }
        return totalValue;
    }

    private int getTotalProtein() {
        int totalValue = 0;
        for (Food food : foods) {
            totalValue += food.getProtein();
        }
        return totalValue;
    }

    private int getTotalFat() {
        int totalValue = 0;
        for (Food food : foods) {
            totalValue += food.getFat();
        }
        return totalValue;
    }

    private int getTotalSodium() {
        int totalValue = 0;
        for (Food food : foods) {
            totalValue += food.getSodium();
        }
        return totalValue;
    }

    private int getTotalCholesterol() {
        int totalValue = 0;
        for (Food food : foods) {
            totalValue += food.getCholesterol();
        }
        return totalValue;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
