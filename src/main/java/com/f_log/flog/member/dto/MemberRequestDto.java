package com.f_log.flog.member.dto;

import com.f_log.flog.member.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRequestDto {
    private final String loginId;
    private final String password;
    private final String name;
    private final Gender gender;
    private final int age;
}

