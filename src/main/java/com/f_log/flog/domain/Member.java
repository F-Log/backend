package com.f_log.flog.domain;

import com.f_log.flog.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Entity
@Getter
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Inbody> inbody = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_information_id")
    private HealthInformation healthInformation;

    @OneToOne(mappedBy = "exercise")
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
}
