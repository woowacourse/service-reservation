package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartMinuteBlock {
    private StartMinute start_minute;

    public StartMinuteBlock() {
    }

    public StartMinuteBlock(StartMinute start_minute) {
        this.start_minute = start_minute;
    }

    public StartMinute getStart_minute() {
        return start_minute;
    }
}
