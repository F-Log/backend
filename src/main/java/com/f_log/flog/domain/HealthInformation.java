package com.f_log.flog.domain;

import com.f_log.flog.member.domain.Member;
import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class HealthInformation extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "health_information_id")
    private Long id;

    @OneToOne(mappedBy = "healthInformation")
    private Member member;

    @Enumerated(EnumType.STRING)
    private DailyActivity dailyActivity;
}
