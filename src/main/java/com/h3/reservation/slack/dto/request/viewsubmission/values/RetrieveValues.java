package com.h3.reservation.slack.dto.request.viewsubmission.values;

import com.h3.reservation.slack.dto.request.viewsubmission.values.block.*;

public class RetrieveValues {
    private StartHourBlock startHourBlock;
    private StartMinuteBlock startMinuteBlock;
    private EndHourBlock endHourBlock;
    private EndMinuteBlock endMinuteBlock;
    private DatepickerBlock datepickerBlock;

    public RetrieveValues() {
    }

    public RetrieveValues(StartHourBlock startHourBlock, StartMinuteBlock startMinuteBlock, EndHourBlock endHourBlock,
                          EndMinuteBlock endMinuteBlock, DatepickerBlock datepickerBlock) {
        this.startHourBlock = startHourBlock;
        this.startMinuteBlock = startMinuteBlock;
        this.endHourBlock = endHourBlock;
        this.endMinuteBlock = endMinuteBlock;
        this.datepickerBlock = datepickerBlock;
    }

    public StartHourBlock getStartHourBlock() {
        return startHourBlock;
    }

    public StartMinuteBlock getStartMinuteBlock() {
        return startMinuteBlock;
    }

    public EndHourBlock getEndHourBlock() {
        return endHourBlock;
    }

    public EndMinuteBlock getEndMinuteBlock() {
        return endMinuteBlock;
    }

    public DatepickerBlock getDatepickerBlock() {
        return datepickerBlock;
    }
}
