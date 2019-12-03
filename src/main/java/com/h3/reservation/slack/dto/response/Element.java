package com.h3.reservation.slack.dto.response;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Element {
    private String type;
    private Text text;
    private String value;

    public Element() {
    }

    public Element(String type, Text text, String value) {
        this.type = type;
        this.text = text;
        this.value = value;
    }

    public static Element textButton(String text, String value) {
        return new Element("button", new Text(text), value);
    }

    public String getType() {
        return type;
    }

    public Text getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
