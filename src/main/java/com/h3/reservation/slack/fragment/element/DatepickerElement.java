package com.h3.reservation.slack.fragment.element;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class DatepickerElement extends Element {
    private String actionId;

    public DatepickerElement() {
        super(ElementType.DATEPICKER);
    }

    public DatepickerElement(String actionId) {
        super(ElementType.DATEPICKER);
        this.actionId = actionId;
    }

    public String getActionId() {
        return actionId;
    }
}
