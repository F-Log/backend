package com.f_log.flog.inbody.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.inbodyfeedback.domain.InbodyFeedback;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Inbody extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "inbody_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID inbodyUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid")
    private Member member;

    @OneToOne(mappedBy = "inbody")
    private InbodyFeedback inbodyFeedback;

    @Column(name = "body_weight")
    private float bodyWeight;

    @Column(name = "height")
    private float height;

    @Column(name = "muscle_mass")
    private float muscleMass;

    @Column(name = "fat_free_mass")
    private float fatFreeMass;

    @Column(name = "body_fat_percentage")
    private float bodyFatPercentage;

    @Column(name = "fat_mass")
    private float fatMass;

    @Column(name = "basal_metabolic_rate")
    private float basalMetabolicRate;

    public Inbody() {
    }

    public Inbody(Member member, float bodyWeight, float height, float muscleMass, float bodyFatPercentage, float fatMass, float basalMetabolicRate) {
        this.member = member;
        this.bodyWeight = bodyWeight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.fatFreeMass = fatFreeMass;
        this.bodyFatPercentage = bodyFatPercentage;
        this.fatMass = fatMass;
        this.basalMetabolicRate = basalMetabolicRate;
    }
}
