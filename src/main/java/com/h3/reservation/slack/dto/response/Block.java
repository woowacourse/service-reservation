package com.h3.reservation.slack.dto.response;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Block {
    private String type;
    private List<Element> elements;

    public Block() {
    }

    public Block(String type, List<Element> elements) {
        this.type = type;
        this.elements = elements;
    }

    public String getType() {
        return type;
    }

    public List<Element> getElements() {
        return elements;
    }
}
