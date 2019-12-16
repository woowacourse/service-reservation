package com.h3.reservation.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.fragment.composition.Option;
import com.h3.reservation.slack.fragment.composition.text.PlainText;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaticSelectElement extends Element {
    private PlainText placeholder;
    private String actionId;
    private Option initialOption;
    private List<Option> options;

    public StaticSelectElement() {
        super(ElementType.STATIC_SELECT);
    }

    public StaticSelectElement(PlainText placeholder, String actionId, List<Option> options) {
        super(ElementType.STATIC_SELECT);
        this.placeholder = placeholder;
        this.actionId = actionId;
        this.options = options;
    }

    public StaticSelectElement(PlainText placeholder, String actionId, Option initialOption, List<Option> options) {
        super(ElementType.STATIC_SELECT);
        this.placeholder = placeholder;
        this.actionId = actionId;
        this.initialOption = initialOption;
        this.options = options;
    }

    public PlainText getPlaceholder() {
        return placeholder;
    }

    public String getActionId() {
        return actionId;
    }

    public Option getInitialOption() {
        return initialOption;
    }

    public List<Option> getOptions() {
        return options;
    }
}
