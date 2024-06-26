package com.f_log.flog.healthinformation.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
public class HealthInformation extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "health_information_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID uuid;

    @OneToOne(mappedBy = "healthInformation")
    private Member member;

    @Enumerated(EnumType.STRING)
    private DailyActivity dailyActivity;

    /**
     * DailyActivity를 업데이트하는 메서드.
     *
     * @param newActivity 새로운 DailyActivity 값
     */
    public void changeDailyActivity(DailyActivity newActivity) {
        this.dailyActivity = newActivity;
    }

    /**
     * HealthInformation 객체를 생성할 때 멤버를 설정합니다.
     *
     * @param member 해당 건강 정보를 가지는 회원 객체
     */
    public void setMember(Member member) {
        this.member = member;
    }
}
