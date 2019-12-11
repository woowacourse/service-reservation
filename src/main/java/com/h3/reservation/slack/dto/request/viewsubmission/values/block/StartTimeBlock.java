package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartTimeBlock {
    private StartTime start_time;

    public StartTimeBlock() {
    }

    public StartTimeBlock(StartTime start_time) {
        this.start_time = start_time;
    }

    public StartTime getStart_time() {
        return start_time;
    }
}
