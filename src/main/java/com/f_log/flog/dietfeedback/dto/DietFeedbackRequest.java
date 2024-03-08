package com.f_log.flog.dietfeedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietFeedbackRequest {
    private UUID memberUuid;
    private LocalDate date;
    private String dietFeedback;
}
