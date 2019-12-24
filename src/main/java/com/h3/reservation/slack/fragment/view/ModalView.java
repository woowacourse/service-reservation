package com.h3.reservation.slack.fragment.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h3.reservation.slack.dto.response.common.ModalSubmissionType;
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
    private ModalSubmissionType callbackId;
    private String privateMetadata;
    private PlainText title;
    private PlainText submit;
    private PlainText close;
    private List<Block> blocks;
    private boolean clearOnClose;

    public ModalView() {
    }

    public ModalView(ModalSubmissionType callbackId, PlainText title, PlainText close, List<Block> blocks) {
        this.callbackId = callbackId;
        this.title = title;
        this.close = close;
        this.blocks = blocks;
    }

    public ModalView(ModalSubmissionType callbackId, PlainText title, PlainText submit, PlainText close, List<Block> blocks) {
        this.callbackId = callbackId;
        this.title = title;
        this.submit = submit;
        this.close = close;
        this.blocks = blocks;
    }

    public ModalView(ModalSubmissionType callbackId, String privateMetadata, PlainText title, PlainText close, List<Block> blocks) {
        this.callbackId = callbackId;
        this.privateMetadata = privateMetadata;
        this.title = title;
        this.close = close;
        this.blocks = blocks;
    }

    public ModalView(ModalSubmissionType callbackId, String privateMetadata, PlainText title,
                     PlainText submit, PlainText close, List<Block> blocks) {
        this.callbackId = callbackId;
        this.privateMetadata = privateMetadata;
        this.title = title;
        this.submit = submit;
        this.close = close;
        this.blocks = blocks;
    }

    public ModalView(ModalSubmissionType callbackId, PlainText title, PlainText close, List<Block> blocks, boolean clearOnClose) {
        this.callbackId = callbackId;
        this.title = title;
        this.close = close;
        this.blocks = blocks;
        this.clearOnClose = clearOnClose;
    }

    public String getType() {
        return type;
    }

    public ModalSubmissionType getCallbackId() {
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

    public String getPrivateMetadata() {
        return privateMetadata;
    }

    public boolean isClearOnClose() {
        return clearOnClose;
    }
}
