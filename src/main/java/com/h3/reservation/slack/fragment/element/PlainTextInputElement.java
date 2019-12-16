package com.h3.reservation.slack.fragment.element;

import com.h3.reservation.slack.fragment.composition.text.PlainText;

public class PlainTextInputElement extends Element {
    private String actionId;
    private PlainText placeholder;
    private String initialValue;

    public PlainTextInputElement() {
        super(ElementType.PLAIN_TEXT_INPUT);
    }

    public PlainTextInputElement(String actionId, PlainText placeholder) {
        super(ElementType.PLAIN_TEXT_INPUT);
        this.actionId = actionId;
        this.placeholder = placeholder;
    }

    public PlainTextInputElement(String actionId, PlainText placeholder, String initialValue) {
        super(ElementType.PLAIN_TEXT_INPUT);
        this.actionId = actionId;
        this.placeholder = placeholder;
        this.initialValue = initialValue;
    }

    public String getActionId() {
        return actionId;
    }

    public PlainText getPlaceholder() {
        return placeholder;
    }

    public String getInitialValue() {
        return initialValue;
    }
}
