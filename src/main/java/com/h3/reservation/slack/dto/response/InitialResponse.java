package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.block.ActionsBlock;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class InitialResponse {
    private String channel;
    private List<ActionsBlock> blocks;

    public InitialResponse() {
    }

    public InitialResponse(String channel, List<ActionsBlock> blocks) {
        this.channel = channel;
        this.blocks = blocks;
    }

    public String getChannel() {
        return channel;
    }

    public List<ActionsBlock> getBlocks() {
        return blocks;
    }
}
