package com.h3.reservation.slack.fragment.block;


import com.h3.reservation.slack.fragment.element.Element;
import com.h3.reservation.slack.fragment.text.Text;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class InputBlock extends Block {
    private Text label;
    private Element element;

    public InputBlock() {
    }

    public InputBlock(Text label, Element element) {
        super(BlockType.INPUT);
        this.label = label;
        this.element = element;
    }

    public Text getLabel() {
        return label;
    }

    public Element getElement() {
        return element;
    }
}