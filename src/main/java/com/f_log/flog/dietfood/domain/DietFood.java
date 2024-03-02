package com.f_log.flog.dietfood.domain;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.food.domain.Food;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietFood extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    private Diet diet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    private int quantity; // 수량

    private String notes; // 비고

    @Builder
    public DietFood(Diet diet, Food food, int quantity, String notes) {
        this.diet = diet;
        this.food = food;
        this.quantity = quantity;
        this.notes = notes;
    }

    // 수량 업데이트 메소드
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    // 비고 업데이트 메소드
    public void updateNotes(String notes) {
        this.notes = notes;
    }
}
