package com.f_log.flog.inbodyfeedback.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.inbody.domain.Inbody;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class InbodyFeedback extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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
