package com.f_log.flog.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Food {

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
