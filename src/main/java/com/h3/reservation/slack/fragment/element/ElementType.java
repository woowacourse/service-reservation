package com.h3.reservation.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public enum ElementType {
    BUTTON("button"),
    DATEPICKER("datepicker"),
    IMAGE("image"),
    MULTI_STATIC_SELECT("multi_static_select"),
    PLAIN_TEXT_INPUT("plain_text_input"),
    STATIC_SELECT("static_select"),
    OVERFLOW("overflow");

    @JsonValue
    private String type;

    ElementType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
