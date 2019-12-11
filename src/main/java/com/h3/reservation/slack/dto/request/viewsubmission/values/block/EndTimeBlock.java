package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class EndTimeBlock {
    private EndTime end_time;

    public EndTimeBlock() {
    }

    public EndTimeBlock(EndTime end_time) {
        this.end_time = end_time;
    }

    public EndTime getEnd_time() {
        return end_time;
    }
}
