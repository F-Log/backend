package com.f_log.flog.diet.domain;

import com.f_log.flog.domain.DietFeedback;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
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

    // TODO: add Foods, implement total nutrient calculation method using foods
    @Builder
    public Diet(UUID uuid,
                Member member,
                int totalCarbohydrate,
                int totalProtein,
                int totalFat,
                int totalSodium,
                int totalCholesterol,
                MealType mealType,
                LocalDateTime mealDate) {
        this.uuid = uuid;
        this.member = member;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.totalSodium = totalSodium;
        this.totalCholesterol = totalCholesterol;
        this.mealType = mealType;
        this.mealDate = mealDate;
    }

    private void addFoods(Food food) {
        foods.add(food);
    }

    public void updateTotalCarbohydrate(int totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public void updateTotalProtein(int totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void updateTotalFat(int totalFat) {
        this.totalFat = totalFat;
    }

    public void updateTotalSodium(int totalSodium) {
        this.totalSodium = totalSodium;
    }

    public void updateTotalCholesterol(int totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public void updateMealDate(LocalDateTime mealDate) {
        this.mealDate = mealDate;
    }

    public void updateMealType(MealType mealType) {
        this.mealType = mealType;
    }

}
