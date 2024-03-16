package com.f_log.flog.gpt.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateDailyDietFeedbackRequest {
    UUID memberUuid;
    LocalDate date;
}
