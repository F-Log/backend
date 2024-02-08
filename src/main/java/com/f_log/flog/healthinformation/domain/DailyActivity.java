package com.f_log.flog.healthinformation.domain;

public enum DailyActivity {
    NO_ACTIVITY("활동이 적거나 운동을 안하는 경우 = 기초대사량 x 0.2kcal"),
    LIGHT_ACTIVITY("가벼운 활동 및 운동을 하는 경우(1~3일/1주) = 기초대사량 x 0.375kcal"),
    MODERATE_ACTIVITY("보통의 활동 및 운동을 하는 경우(3~5일/1주) = 기초대사량 x 0.555kcal"),
    ACTIVE_ACTIVITY("적극적인 활동 및 운동을 하는 경우(6~7일/1주) = 기초대사량 x 0.725kcal"),
    VERY_ACTIVE_ACTIVITY("매우 적극적인 활동 및 운동, 운동선수 = 기초대사량 x 0.9kcal");

    private final String description;

    DailyActivity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
