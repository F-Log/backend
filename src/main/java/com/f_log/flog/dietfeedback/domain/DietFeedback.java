package com.f_log.flog.dietfeedback.domain;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.inbody.domain.Inbody;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DietFeedback extends BaseEntity{

    /* TODO: Feedback 인터페이스/추상클래스 구현*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "diet_feedback_id")
    private Long id;

    @Column(name = "diet_feedback")
    private String dietFeedback;

    @OneToOne
    @JoinColumn(name = "diet_id")
    private Diet diet;

    @Builder
    public DietFeedback(String dietFeedback, Diet diet) {
        this.dietFeedback = dietFeedback;
        this.diet = diet;
    }

    public void setDietFeedback(String dietFeedback) {
        this.dietFeedback = dietFeedback;
    }
}