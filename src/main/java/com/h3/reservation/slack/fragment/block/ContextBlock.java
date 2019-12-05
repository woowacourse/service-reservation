package com.h3.reservation.slack.fragment.block;

import com.h3.reservation.slack.fragment.composition.text.Text;

import java.util.List;

public class ContextBlock extends Block {
    private List<Text> elements;

    public ContextBlock() {
        super(BlockType.CONTEXT);
    }

    public ContextBlock(List<Text> elements) {
        super(BlockType.CONTEXT);
        this.elements = elements;
    }

    public List<Text> getElements() {
        return elements;
    }
}
