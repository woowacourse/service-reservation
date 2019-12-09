package com.h3.reservation.slack.fragment.block;

import com.h3.reservation.slack.fragment.composition.text.Text;

import java.util.List;

public class ContextBlock extends Block {
    private String block_id;
    private List<Text> elements;

    public ContextBlock() {
        super(BlockType.CONTEXT);
    }

    public ContextBlock(String block_id, List<Text> elements) {
        super(BlockType.CONTEXT);
        this.block_id = block_id;
        this.elements = elements;
    }

    public List<Text> getElements() {
        return elements;
    }
}
