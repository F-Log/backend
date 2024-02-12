package com.f_log.flog.healthinformation.dto;

import com.f_log.flog.healthinformation.domain.DailyActivity;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthInformationRequestDto {
    private UUID memberUuid;
    private DailyActivity dailyActivity;
}
