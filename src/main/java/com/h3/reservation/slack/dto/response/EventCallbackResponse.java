package com.h3.reservation.slack.dto.response;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class EventCallbackResponse {
    private String channel;
    private List<Block> blocks;

    public EventCallbackResponse() {
    }

    public EventCallbackResponse(String channel, List<Block> blocks) {
        this.channel = channel;
        this.blocks = blocks;
    }

    public String getChannel() {
        return channel;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
