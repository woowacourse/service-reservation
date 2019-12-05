package com.h3.reservation.slack.fragment.element;

import com.h3.reservation.slack.fragment.composition.text.PlainText;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class ButtonElement extends Element {
    private PlainText text;
    private String action_id;

    public ButtonElement() {
        super(ElementType.BUTTON);
    }

    public ButtonElement(PlainText text, String action_id) {
        super(ElementType.BUTTON);
        this.text = text;
        this.action_id = action_id;
    }

    public PlainText getText() {
        return text;
    }

    public String getAction_id() {
        return action_id;
    }
}
