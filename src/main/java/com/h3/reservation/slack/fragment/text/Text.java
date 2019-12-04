package com.h3.reservation.slack.fragment.text;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Text {
    protected TextType type;
    protected String text;
    private boolean emoji = true;

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

    public boolean getEmoji() {
        return emoji;
    }
}
