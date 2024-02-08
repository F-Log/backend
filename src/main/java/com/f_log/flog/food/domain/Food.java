package com.f_log.flog.food.domain;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Food extends BaseEntity {

    @Id
    @GeneratedValue
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
}
