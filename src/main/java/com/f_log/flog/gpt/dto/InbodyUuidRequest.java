package com.f_log.flog.gpt.dto;

import java.util.UUID;

public class InbodyUuidRequest {
    private UUID inbodyUuid;

    // 기본 생성자 및 getter/setter
    public UUID getInbodyUuid() {
        return inbodyUuid;
    }

    public void setInbodyUuid(UUID inbodyUuid) {
        this.inbodyUuid = inbodyUuid;
    }
}