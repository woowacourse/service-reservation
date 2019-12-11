package com.h3.reservation.slack.dto.request.viewsubmission.values;

import com.h3.reservation.slack.dto.request.viewsubmission.values.block.*;

public class RetrieveValues {
    private StartTimeBlock startTimeBlock;
    private StartMinuteBlock startMinuteBlock;
    private EndTimeBlock endTimeBlock;
    private EndMinuteBlock endMinuteBlock;
    private DatepickerBlock datepickerBlock;

    public RetrieveValues() {
    }

    public RetrieveValues(StartTimeBlock startTimeBlock, StartMinuteBlock startMinuteBlock, EndTimeBlock endTimeBlock,
                          EndMinuteBlock endMinuteBlock, DatepickerBlock datepickerBlock) {
        this.startTimeBlock = startTimeBlock;
        this.startMinuteBlock = startMinuteBlock;
        this.endTimeBlock = endTimeBlock;
        this.endMinuteBlock = endMinuteBlock;
        this.datepickerBlock = datepickerBlock;
    }

    public StartTimeBlock getStartTimeBlock() {
        return startTimeBlock;
    }

    public StartMinuteBlock getStartMinuteBlock() {
        return startMinuteBlock;
    }

    public EndTimeBlock getEndTimeBlock() {
        return endTimeBlock;
    }

    public EndMinuteBlock getEndMinuteBlock() {
        return endMinuteBlock;
    }

    public DatepickerBlock getDatepickerBlock() {
        return datepickerBlock;
    }
}
