package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class EndMinuteBlock {
    private EndMinute endMinute;

    public EndMinuteBlock() {
    }

    public EndMinuteBlock(EndMinute endMinute) {
        this.endMinute = endMinute;
    }

    public EndMinute getEndMinute() {
        return endMinute;
    }
}
