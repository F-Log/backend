package com.f_log.flog.healthinformation.dto;

import com.f_log.flog.healthinformation.domain.DailyActivity;
import com.f_log.flog.healthinformation.domain.HealthInformation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthInformationResponseDto {
    private DailyActivity dailyActivity;

    /**
     * HealthInformation 엔티티를 HealthInformationResponseDto로 변환합니다.
     *
     * @param healthInformation HealthInformation 엔티티
     * @return 변환된 HealthInformationResponseDto 객체
     */
    public static HealthInformationResponseDto fromEntity(HealthInformation healthInformation) {
        return HealthInformationResponseDto.builder()
                .dailyActivity(healthInformation.getDailyActivity())
                .build();
    }
}
