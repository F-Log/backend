package com.f_log.flog.healthinformation.dto;

import com.f_log.flog.healthinformation.domain.DailyActivity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class HealthInformationRequestDto {
    private UUID memberUuid;
    private DailyActivity dailyActivity;
}
