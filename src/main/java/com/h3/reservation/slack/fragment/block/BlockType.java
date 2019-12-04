package com.h3.reservation.slack.fragment.block;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public enum BlockType {
    SECTION("section"),
    DIVIDER("divider"),
    IMAGE("image"),
    ACTIONS("actions"),
    CONTEXT("context"),
    INPUT("input");

    @JsonValue
    private String type;

    BlockType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
