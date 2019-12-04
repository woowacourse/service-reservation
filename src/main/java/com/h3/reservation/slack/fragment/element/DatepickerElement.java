package com.h3.reservation.slack.fragment.element;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class DatepickerElement extends Element {
    private String action_id;

    public DatepickerElement() {
    }

    public DatepickerElement(String action_id) {
        super(ElementType.DATEPICKER);
        this.action_id = action_id;
    }

    public String getAction_id() {
        return action_id;
    }
}
