package com.f_log.flog.healthinformation.domain;

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
import java.util.UUID;
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

    @Column(name = "member_uuid")
    private UUID memberUuid;

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
        if (member != null) {
            this.memberUuid = member.getUuid();
        }
    }
}