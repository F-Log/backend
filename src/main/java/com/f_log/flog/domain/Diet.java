package com.f_log.flog.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diet extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "diet_id")
    private Long id;

    /* TODO: s3 url 추가 */

    @OneToMany(mappedBy = "diet")
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

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @OneToOne(mappedBy = "diet", fetch = FetchType.LAZY)
    private DietFeedback dietFeedback;

    // member와의 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
