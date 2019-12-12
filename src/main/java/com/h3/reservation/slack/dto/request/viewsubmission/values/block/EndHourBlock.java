package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class EndHourBlock {
    private EndHour endHour;

    public EndHourBlock() {
    }

    public EndHourBlock(EndHour endHour) {
        this.endHour = endHour;
    }

    public EndHour getEndHour() {
        return endHour;
    }
}
