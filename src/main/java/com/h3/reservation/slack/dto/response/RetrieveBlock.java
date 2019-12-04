package com.h3.reservation.slack.dto.response;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class RetrieveBlock {
    private String type;
    private DatePicker elements;
    private Text label;

    public RetrieveBlock() {
    }

    public RetrieveBlock(String type, DatePicker elements, Text label) {
        this.type = type;
        this.elements = elements;
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public DatePicker getElements() {
        return elements;
    }

    public Text getLabel() {
        return label;
    }
}
