package com.f_log.flog.inbodyfeedback.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.inbody.domain.Inbody;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class InbodyFeedback extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    @Column(name = "inbody_feedback")
    private String inbodyFeedback;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbody_id", columnDefinition = "BINARY(16)")
    private Inbody inbody;

    public InbodyFeedback() {}

    public InbodyFeedback(String inbodyFeedback, Inbody inbody) {
        this.inbodyFeedback = inbodyFeedback;
        this.inbody = inbody;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
