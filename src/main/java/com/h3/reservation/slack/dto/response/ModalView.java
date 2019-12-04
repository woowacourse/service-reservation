package com.h3.reservation.slack.dto.response;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class ModalView {
    private String type;
    private Text title;
    private Text submit;
    private Text close;
    private List<RetrieveBlock> blocks;

    public ModalView() {
    }

    public ModalView(String type, Text title, Text submit, Text close, List<RetrieveBlock> blocks) {
        this.type = type;
        this.title = title;
        this.submit = submit;
        this.close = close;
        this.blocks = blocks;
    }

    public String getType() {
        return type;
    }

    public Text getTitle() {
        return title;
    }

    public Text getSubmit() {
        return submit;
    }

    public Text getClose() {
        return close;
    }

    public List<RetrieveBlock> getBlocks() {
        return blocks;
    }
}
