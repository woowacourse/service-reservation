package com.h3.reservation.slack.fragment.error;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-12
 */
public class DatePickerErrors extends Errors {
    private String datepickerBlock;

    public DatePickerErrors() {
    }

    public DatePickerErrors(String datepickerBlock) {
        this.datepickerBlock = datepickerBlock;
    }

    public String getDatepickerBlock() {
        return datepickerBlock;
    }
}
