package com.f_log.flog.inbodyfeedback.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.inbody.domain.Inbody;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
public class InbodyFeedback extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "inbody_feedback_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "inbody_feedback", length = 10000)
    private String inbodyFeedback;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbody_id", columnDefinition = "BINARY(16)")
    private Inbody inbody;

    public InbodyFeedback() {}

    public InbodyFeedback(String inbodyFeedback, Inbody inbody) {
        this.inbodyFeedback = inbodyFeedback;
        this.inbody = inbody;
    }

    public void setId(UUID uuid) {
        this.uuid = uuid;
    }
}
