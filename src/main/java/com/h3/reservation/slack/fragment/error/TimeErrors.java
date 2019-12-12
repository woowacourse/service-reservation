package com.h3.reservation.slack.fragment.error;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-12
 */
public class TimeErrors extends Errors {
    private String startHourBlock;

    public TimeErrors() {
    }

    public TimeErrors(String startHourBlock) {
        this.startHourBlock = startHourBlock;
    }

    public String getStartHourBlock() {
        return startHourBlock;
    }
}
