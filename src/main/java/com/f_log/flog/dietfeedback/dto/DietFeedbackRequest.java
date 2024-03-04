package com.f_log.flog.dietfeedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietFeedbackRequest {
    private UUID dietUuid;
    private String dietFeedback;
}
