package com.h3.reservation.slack.dto.response;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Text {
    private String type;
    private String text;
    private boolean emoji;

    public Text() {
    }

    public Text(String type, String text, boolean emoji) {
        this.type = type;
        this.text = text;
        this.emoji = emoji;
    }

    public Text(String text) {
        this("plain_text", text, true);
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public boolean getEmoji() {
        return emoji;
    }
}
