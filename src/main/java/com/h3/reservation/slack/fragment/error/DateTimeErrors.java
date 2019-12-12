package com.h3.reservation.slack.fragment.error;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-12
 */
public class DateTimeErrors extends Errors {
    private String datepickerBlock;

    public DateTimeErrors() {
    }

    public DateTimeErrors(String datepickerBlock) {
        this.datepickerBlock = datepickerBlock;
    }

    public String getDatepickerBlock() {
        return datepickerBlock;
    }
}
