package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class EndMinuteBlock {
    private EndMinute end_minute;

    public EndMinuteBlock() {
    }

    public EndMinuteBlock(EndMinute end_minute) {
        this.end_minute = end_minute;
    }

    public EndMinute getEnd_minute() {
        return end_minute;
    }
}
