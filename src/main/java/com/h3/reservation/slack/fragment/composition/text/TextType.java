package com.h3.reservation.slack.fragment.composition.text;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public enum TextType {
    PLAIN_TEXT("plain_text"),
    MRKDWN("mrkdwn");

    @JsonValue
    private String type;

    TextType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
