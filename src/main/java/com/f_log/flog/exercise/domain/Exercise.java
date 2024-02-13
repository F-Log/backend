package com.f_log.flog.exercise.domain;

import com.f_log.flog.member.domain.Member;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "member_uuid", nullable = false)
    private UUID memberUuid;

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

    @OneToOne(mappedBy = "exercise")
    private Member member;

    /**
     * 멤버의 UUID를 설정합니다.
     *
     * @param memberUuid 멤버의 UUID
     */
    public void setMemberUuid(UUID memberUuid) {
        this.memberUuid = memberUuid;
    }

    /**
     * 멤버의 UUID를 반환합니다.
     *
     * @return 멤버 UUID
     */
    public UUID getMemberUuid() {
        return this.memberUuid;
    }

    public Exercise(UUID memberUuid, String exerciseType, float targetWeight, float exerciseFrequency, ExerciseIntensity exerciseIntensity, ExercisePurpose exercisePurpose) {
        this.memberUuid = memberUuid;
        this.exerciseType = exerciseType;
        this.targetWeight = targetWeight;
        this.exerciseFrequency = exerciseFrequency;
        this.exerciseIntensity = exerciseIntensity;
        this.exercisePurpose = exercisePurpose;
    }

    protected Exercise() {}

    // 엔티티 상태 업데이트를 위한 메소드
    public void updateExercise(String exerciseType, float targetWeight, float exerciseFrequency,
                               ExerciseIntensity exerciseIntensity, ExercisePurpose exercisePurpose) {
        this.exerciseType = exerciseType;
        this.targetWeight = targetWeight;
        this.exerciseFrequency = exerciseFrequency;
        this.exerciseIntensity = exerciseIntensity;
        this.exercisePurpose = exercisePurpose;
    }
}
