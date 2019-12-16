package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartHourBlock {
    private OptionWrapper startHour;

    public StartHourBlock() {
    }

    public StartHourBlock(OptionWrapper startHour) {
        this.startHour = startHour;
    }

    public OptionWrapper getStartHour() {
        return startHour;
    }
}
