package com.h3.reservation.slack.fragment.element;

import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.PlainText;

import java.util.List;

public class StaticSelectElement extends Element {
    private PlainText placeholder;
    private String action_id;
    private Option initial_option;
    private List<Option> options;

    public StaticSelectElement() {
    }

    public StaticSelectElement(PlainText placeholder, String action_id, Option initial_option, List<Option> options) {
        super(ElementType.STATIC_SELECT);
        this.placeholder = placeholder;
        this.action_id = action_id;
        this.initial_option = initial_option;
        this.options = options;
    }

    public PlainText getPlaceholder() {
        return placeholder;
    }

    public String getAction_id() {
        return action_id;
    }

    public Option getInitial_option() {
        return initial_option;
    }

    public List<Option> getOptions() {
        return options;
    }
}
