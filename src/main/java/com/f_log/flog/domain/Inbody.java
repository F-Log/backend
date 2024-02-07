package com.f_log.flog.domain;

import com.f_log.flog.Member.domain.Member;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Inbody extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inbody_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "inbody")
    private InbodyFeedback inbodyFeedback;

    @Column(name = "body_weight")
    private float bodyWeight;

    @Column(name = "height")
    private float height;

    @Column(name = "muscle_mass")
    private float muscleMass;

    @Column(name = "body_fat_percentage")
    private float bodyFatPercentage;

    @Column(name = "fat_mass")
    private float fatMass;

    @Column(name = "basal_metabolic_rate")
    private float basalMetabolicRate;
}
