package com.h3.reservation.slack.dto.response;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class DatePicker {
    private String type;
    private String initial_date;
    private Text placeholder;

    public DatePicker() {
    }

    public DatePicker(String type, String initial_date, Text placeholder) {
        this.type = type;
        this.initial_date = initial_date;
        this.placeholder = placeholder;
    }

    public String getType() {
        return type;
    }

    public String getInitial_date() {
        return initial_date;
    }

    public Text getPlaceholder() {
        return placeholder;
    }
}
