package com.f_log.flog.member.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginResponseDto {
    private UUID uuid;
    private String loginId;
}
