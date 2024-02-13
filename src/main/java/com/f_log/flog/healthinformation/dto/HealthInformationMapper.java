package com.f_log.flog.healthinformation.dto;

import com.f_log.flog.healthinformation.domain.DailyActivity;
import com.f_log.flog.healthinformation.domain.HealthInformation;
import org.springframework.stereotype.Component;

@Component
public class HealthInformationMapper {

    /**
     * HealthInformationRequestDto를 HealthInformation 엔티티로 변환합니다.
     *
     * @param requestDto HealthInformationRequestDto 객체
     * @return 변환된 HealthInformation 엔티티
     */
    public HealthInformation toEntity(HealthInformationRequestDto requestDto) {
        HealthInformation healthInformation = new HealthInformation();
        DailyActivity dailyActivity = requestDto.getDailyActivity();
        healthInformation.changeDailyActivity(dailyActivity);
        return healthInformation;
    }
}
