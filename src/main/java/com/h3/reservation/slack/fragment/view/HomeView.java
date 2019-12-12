package com.h3.reservation.slack.fragment.view;

import com.h3.reservation.slack.fragment.block.Block;

import java.util.List;

public class HomeView {
    private final String type = "home";
    private List<Block> blocks;

    public HomeView() {
    }

    public HomeView(List<Block> blocks) {
        this.blocks = blocks;
    }

    public String getType() {
        return type;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
