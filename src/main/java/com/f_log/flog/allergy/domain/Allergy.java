package com.f_log.flog.allergy.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Allergy extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "allergy")
    private String allergy;

    @Builder
    public Allergy(Member member, String allergy) {
        this.member = member;
        this.allergy = allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
