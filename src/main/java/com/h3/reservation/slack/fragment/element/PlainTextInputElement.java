package com.h3.reservation.slack.fragment.element;

import com.h3.reservation.slack.fragment.composition.text.PlainText;

public class PlainTextInputElement extends Element {
    private String action_id;
    private PlainText placeholder;

    public PlainTextInputElement() {
        super(ElementType.PLAIN_TEXT_INPUT);
    }

    public PlainTextInputElement(String action_id, PlainText placeholder) {
        super(ElementType.PLAIN_TEXT_INPUT);
        this.action_id = action_id;
        this.placeholder = placeholder;
    }

    public String getAction_id() {
        return action_id;
    }

    public PlainText getPlaceholder() {
        return placeholder;
    }
}
