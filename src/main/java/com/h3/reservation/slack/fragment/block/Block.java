package com.h3.reservation.slack.fragment.block;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public abstract class Block {
    private BlockType type;

    public Block() {
    }

    public Block(BlockType type) {
        this.type = type;
    }

    public BlockType getType() {
        return type;
    }
}
