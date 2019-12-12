package com.h3.reservation.slack.dto.request.viewsubmission.values.block;

public class StartMinuteBlock {
    private OptionWrapper startMinute;

    public StartMinuteBlock() {
    }

    public StartMinuteBlock(OptionWrapper startMinute) {
        this.startMinute = startMinute;
    }

    public OptionWrapper getStartMinute() {
        return startMinute;
    }
}
