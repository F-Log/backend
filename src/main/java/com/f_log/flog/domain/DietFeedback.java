package com.f_log.flog.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class DietFeedback extends BaseEntity{

    /* TODO: Feedback 인터페이스/추상클래스 구현*/

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "diet_feedback_id")
    private Long id;

    @Column(name = "diet_feedback")
    private String inbodyFeedback;

    @OneToOne
    @JoinColumn(name = "diet_id")
    private Inbody inbody;
}