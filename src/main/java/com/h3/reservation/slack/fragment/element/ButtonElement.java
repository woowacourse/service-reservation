package com.h3.reservation.slack.fragment.element;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.fragment.composition.text.PlainText;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ButtonElement extends Element {
    private PlainText text;
    private String actionId;
    private String style;

    public ButtonElement() {
        super(ElementType.BUTTON);
    }

    public ButtonElement(PlainText text, String actionId) {
        super(ElementType.BUTTON);
        this.text = text;
        this.actionId = actionId;
    }

    public ButtonElement(PlainText text, String actionId, String style) {
        super(ElementType.BUTTON);
        this.text = text;
        this.actionId = actionId;
        this.style = style;
    }

    public PlainText getText() {
        return text;
    }

    public String getActionId() {
        return actionId;
    }

    public String getStyle() {
        return style;
    }
}
