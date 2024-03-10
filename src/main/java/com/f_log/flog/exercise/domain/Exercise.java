package com.f_log.flog.exercise.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "exercise_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID uuid;

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

    public Exercise(String exerciseType, float targetWeight, float exerciseFrequency, ExerciseIntensity exerciseIntensity, ExercisePurpose exercisePurpose) {
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

    // Set the member for the exercise
    public void setMember(Member member) {
        this.member = member;
    }
}
