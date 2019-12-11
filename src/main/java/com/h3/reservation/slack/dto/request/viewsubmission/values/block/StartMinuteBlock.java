package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartMinuteBlock {
    private StartMinute startMinute;

    public StartMinuteBlock() {
    }

    public StartMinuteBlock(StartMinute startMinute) {
        this.startMinute = startMinute;
    }

    public StartMinute getStartMinute() {
        return startMinute;
    }
}
