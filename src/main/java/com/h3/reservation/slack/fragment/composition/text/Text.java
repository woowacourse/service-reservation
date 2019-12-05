package com.h3.reservation.slack.fragment.composition.text;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public abstract class Text {
    protected TextType type;
    protected String text;

    public Text() {
    }

    public Text(TextType type, String text) {
        this.type = type;
        this.text = text;
    }

    public TextType getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
