package com.f_log.flog.gpt.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateDietFeedbackRequest {
    UUID dietUuid;
    UUID memberUuid;
}
