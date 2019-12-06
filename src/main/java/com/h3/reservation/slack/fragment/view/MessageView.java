package com.h3.reservation.slack.fragment.view;

import com.h3.reservation.slack.fragment.block.Block;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class MessageView {
    private List<Block> blocks;

    public MessageView() {
    }

    public MessageView(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
