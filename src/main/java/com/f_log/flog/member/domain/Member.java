package com.f_log.flog.member.domain;

import com.f_log.flog.allergy.domain.Allergy;
import com.f_log.flog.diet.domain.Diet;
import com.f_log.flog.exercise.domain.Exercise;
import com.f_log.flog.global.domain.BaseEntity;
import com.f_log.flog.healthinformation.domain.HealthInformation;
import com.f_log.flog.inbody.domain.Inbody;
import com.f_log.flog.member.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Inbody> inbody = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_information_id")
    private HealthInformation healthInformation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
  
    @OneToMany(mappedBy = "member")
    private List<Allergy> allergies = new ArrayList<>();

    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "login_id", length = 45)
    private String loginId;

    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age", length = 11)
    private int age;

    // diet와의 1:N 관계
    @OneToMany(mappedBy = "member")
    private List<Diet> diets = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String name, Gender gender, int age, UUID uuid) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.uuid = uuid;
    }

    public void updateMember(String loginId, String password, String name, Gender gender, int age) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public static Member from(MemberRequestDto memberRequestDto) {
        return Member.builder()
                .loginId(memberRequestDto.getLoginId())
                .password(memberRequestDto.getPassword())
                .name(memberRequestDto.getName())
                .gender(memberRequestDto.getGender())
                .age(memberRequestDto.getAge())
                .uuid(UUID.randomUUID())
                .build();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
