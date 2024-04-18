package com.f_log.flog.diet.domain;

import com.f_log.flog.dietfood.domain.DietFood;
import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "diet_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID dietUuid;

    @Column(name = "total_carbohydrate")
    private double totalCarbohydrate;

    @Column(name = "total_protein")
    private double totalProtein;

    @Column(name = "total_fat")
    private double totalFat;

    @Column(name = "total_sodium")
    private double totalSodium;

    @Column(name = "total_cholesterol")
    private double totalCholesterol;

    @Column(name = "total_sugars")
    private double totalSugars;

    @Column(name = "total_calories")
    private double totalCalories;

    @Column(name = "meal_date")
    private LocalDate mealDate;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    // member와의 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid")
    private Member member;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietFood> dietFoods = new ArrayList<>();

    @Column(name = "s3_url")
    private String s3Url;

    // TODO: add Foods, implement total nutrient calculation method using foods
    @Builder
    public Diet(UUID dietUuid,
                Member member,
                double totalCarbohydrate,
                double totalProtein,
                double totalFat,
                double totalSodium,
                double totalCholesterol,
                double totalSugars,
                double totalCalories,
                MealType mealType,
                LocalDate mealDate) {
        this.dietUuid = dietUuid;
        this.member = member;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
        this.totalSodium = totalSodium;
        this.totalCholesterol = totalCholesterol;
        this.totalSugars = totalSugars;
        this.totalCalories = totalCalories;
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.s3Url = s3Url;
    }
    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public void updateTotalCarbohydrate(double totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public void updateTotalProtein(double totalProtein) {
        this.totalProtein = totalProtein;
    }

    public void updateTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public void updateTotalSodium(double totalSodium) {
        this.totalSodium = totalSodium;
    }

    public void updateTotalCholesterol(double totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public void updateTotalSugars(double totalSugars) {
        this.totalSugars = totalSugars;
    }

    public void updateTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public void updateMealDate(LocalDate mealDate) {
        this.mealDate = mealDate;
    }

    public void updateMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public void addNutrients(double carbohydrate, double protein, double fat, double sodium, double cholesterol, double sugars, double calories) {
        this.totalCarbohydrate += carbohydrate;
        this.totalProtein += protein;
        this.totalFat += fat;
        this.totalSodium += sodium;
        this.totalCholesterol += cholesterol;
        this.totalSugars += sugars;
        this.totalCalories += calories;
    }

    public void subtractNutrients(double carbohydrate, double protein, double fat, double sodium, double cholesterol, double sugars, double calories) {
        this.totalCarbohydrate -= carbohydrate;
        this.totalProtein -= protein;
        this.totalFat -= fat;
        this.totalSodium -= sodium;
        this.totalCholesterol -= cholesterol;
        this.totalSugars -= sugars;
        this.totalCalories -= calories;
    }

    public void removeDietFood(DietFood dietFood) {
        dietFood.setDeleted();
        subtractNutrients(
                dietFood.getFood().getCarbohydrate() * dietFood.getQuantity(),
                dietFood.getFood().getProtein() * dietFood.getQuantity(),
                dietFood.getFood().getFat() * dietFood.getQuantity(),
                dietFood.getFood().getSodium() * dietFood.getQuantity(),
                dietFood.getFood().getCholesterol() * dietFood.getQuantity(),
                dietFood.getFood().getSugars() * dietFood.getQuantity(),
                dietFood.getFood().getCalories() * dietFood.getQuantity()
        );
    }

    // override BaseEntity's setDeleted
    @Override
    public void setDeleted(){
        super.setDeleted();
        for (DietFood dietFood : this.dietFoods) {
            removeDietFood(dietFood);
        }
    }

}
