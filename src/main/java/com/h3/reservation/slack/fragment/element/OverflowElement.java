package com.h3.reservation.slack.fragment.element;

import com.h3.reservation.slack.fragment.composition.Option;

import java.util.List;

public class OverflowElement extends Element {
    private String actionId;
    private List<Option> options;

    public OverflowElement() {
        super(ElementType.OVERFLOW);
    }

    public OverflowElement(String actionId, List<Option> options) {
        super(ElementType.OVERFLOW);
        this.actionId = actionId;
        this.options = options;
    }

    public String getActionId() {
        return actionId;
    }

    public List<Option> getOptions() {
        return options;
    }
}
