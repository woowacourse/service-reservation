package com.h3.reservation.slack.dto.request.viewsubmission.values;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.dto.request.viewsubmission.values.block.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveValues {
    private StartTimeBlock start_time_block;
    private StartMinuteBlock start_minute_block;
    private EndTimeBlock end_time_block;
    private EndMinuteBlock end_minute_block;
    private DatepickerBlock datepicker_block;

    public RetrieveValues() {
    }

    public RetrieveValues(StartTimeBlock start_time_block, StartMinuteBlock start_minute_block, EndTimeBlock end_time_block,
                          EndMinuteBlock end_minute_block, DatepickerBlock datepicker_block) {
        this.start_time_block = start_time_block;
        this.start_minute_block = start_minute_block;
        this.end_time_block = end_time_block;
        this.end_minute_block = end_minute_block;
        this.datepicker_block = datepicker_block;
    }

    public StartTimeBlock getStart_time_block() {
        return start_time_block;
    }

    public StartMinuteBlock getStart_minute_block() {
        return start_minute_block;
    }

    public EndTimeBlock getEnd_time_block() {
        return end_time_block;
    }

    public EndMinuteBlock getEnd_minute_block() {
        return end_minute_block;
    }

    public DatepickerBlock getDatepicker_block() {
        return datepicker_block;
    }
}
