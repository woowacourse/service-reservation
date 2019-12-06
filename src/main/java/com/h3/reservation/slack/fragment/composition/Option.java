package com.h3.reservation.slack.fragment.composition;

import com.h3.reservation.slack.fragment.composition.text.PlainText;

public class Option {
    private PlainText text;
    private String value;

    public Option() {
    }

    public Option(PlainText text, String value) {
        this.text = text;
        this.value = value;
    }

    public PlainText getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
