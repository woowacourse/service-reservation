package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class EndTimeBlock {
    private EndTime endTime;

    public EndTimeBlock() {
    }

    public EndTimeBlock(EndTime endTime) {
        this.endTime = endTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }
}
