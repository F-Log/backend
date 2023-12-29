package com.f_log.flog.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "target_weight")
    private float targetWeight;

    @Column(name = "exercise_frequency")
    private float exerciseFrequency;

    @Enumerated(EnumType.STRING)
    private ExerciseIntensity exerciseIntensity;

    @Enumerated(EnumType.STRING)
    private ExercisePurpose exercisePurpose;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
