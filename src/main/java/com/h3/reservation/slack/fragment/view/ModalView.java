package com.h3.reservation.slack.fragment.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.composition.text.PlainText;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModalView {
    private final String type = "modal";
    private String callbackId;
    private PlainText title;
    private PlainText submit;
    private PlainText close;
    private List<Block> blocks;

    public ModalView() {
    }

    public ModalView(String callbackId, PlainText title, PlainText submit, List<Block> blocks) {
        this.callbackId = callbackId;
        this.title = title;
        this.submit = submit;
        this.blocks = blocks;
    }

    public ModalView(String callbackId, PlainText title, PlainText submit, PlainText close, List<Block> blocks) {
        this.callbackId = callbackId;
        this.title = title;
        this.submit = submit;
        this.close = close;
        this.blocks = blocks;
    }

    public String getType() {
        return type;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public PlainText getTitle() {
        return title;
    }

    public PlainText getSubmit() {
        return submit;
    }

    public PlainText getClose() {
        return close;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
