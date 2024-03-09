package com.f_log.flog.dietfeedback.domain;

import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor
public class DietFeedback extends BaseEntity{

    /* TODO: Feedback 인터페이스/추상클래스 구현*/

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "diet_feedback_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID dietfeedbackUuid;

    @Column(name = "diet_feedback", length = 10000)
    private String dietFeedback;

    @Builder
    public DietFeedback(String dietFeedback) {
        this.dietFeedback = dietFeedback;
    }

    public void setDietFeedback(String dietFeedback) {
        this.dietFeedback = dietFeedback;
    }
}