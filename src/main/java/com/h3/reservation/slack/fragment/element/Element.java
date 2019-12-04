package com.h3.reservation.slack.fragment.element;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public abstract class Element {
    private ElementType type;

    public Element() {
    }

    public Element(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return type;
    }
}
