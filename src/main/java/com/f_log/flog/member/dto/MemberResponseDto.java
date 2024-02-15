package com.f_log.flog.member.dto;

import com.f_log.flog.member.domain.Gender;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MemberResponseDto {
    private UUID uuid;
    private String loginId;
    private String password;
    private String name;
    private Gender gender;
    private int age;
}
