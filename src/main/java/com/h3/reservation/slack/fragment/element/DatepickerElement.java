package com.h3.reservation.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatepickerElement extends Element {
    private String actionId;
    private String initialDate;

    public DatepickerElement() {
        super(ElementType.DATEPICKER);
    }

    public DatepickerElement(String actionId) {
        super(ElementType.DATEPICKER);
        this.actionId = actionId;
    }

    public DatepickerElement(String actionId, String initialDate) {
        super(ElementType.DATEPICKER);
        this.actionId = actionId;
        this.initialDate = initialDate;
    }

    public String getActionId() {
        return actionId;
    }

    public String getInitialDate() {
        return initialDate;
    }
}
