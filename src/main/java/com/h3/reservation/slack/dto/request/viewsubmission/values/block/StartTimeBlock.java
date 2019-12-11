package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartTimeBlock {
    private StartTime startTime;

    public StartTimeBlock() {
    }

    public StartTimeBlock(StartTime startTime) {
        this.startTime = startTime;
    }

    public StartTime getStartTime() {
        return startTime;
    }
}
