package com.f_log.flog.allergy.domain;

import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Allergy extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "allergy_uuid", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid")
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
