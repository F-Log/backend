package com.f_log.flog.inbody.domain;

import com.f_log.flog.domain.InbodyFeedback;
import com.f_log.flog.member.domain.Member;
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
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
public class Inbody extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "inbody_id")
//    private Long id;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "inbody_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID inbodyUuid;

    @Column(name = "member_uuid", columnDefinition = "BINARY(16)")
    private UUID memberUuid;

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

    public Inbody() {
    }

    public Inbody(UUID memberUuid, float bodyWeight, float height, float muscleMass, float bodyFatPercentage, float fatMass, float basalMetabolicRate) {
        this.memberUuid = memberUuid;
        this.bodyWeight = bodyWeight;
        this.height = height;
        this.muscleMass = muscleMass;
        this.bodyFatPercentage = bodyFatPercentage;
        this.fatMass = fatMass;
        this.basalMetabolicRate = basalMetabolicRate;
    }
}
