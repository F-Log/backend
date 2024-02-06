package com.f_log.flog.dto;

import com.f_log.flog.domain.Gender;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

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
