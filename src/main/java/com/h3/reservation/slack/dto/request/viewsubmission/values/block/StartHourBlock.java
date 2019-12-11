package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartHourBlock {
    private StartHour startHour;

    public StartHourBlock() {
    }

    public StartHourBlock(StartHour startHour) {
        this.startHour = startHour;
    }

    public StartHour getStartHour() {
        return startHour;
    }
}
