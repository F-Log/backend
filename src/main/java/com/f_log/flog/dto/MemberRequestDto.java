package com.f_log.flog.dto;

import com.f_log.flog.domain.Gender;
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

