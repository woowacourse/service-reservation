package com.h3.reservation.slack.dto.response;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class RetrieveResponse {
    private String trigger_id;
    private ModalView view;

    public RetrieveResponse() {
    }

    public RetrieveResponse(String trigger_id, ModalView view) {
        this.trigger_id = trigger_id;
        this.view = view;
    }

    public String getTrigger_id() {
        return trigger_id;
    }

    public ModalView getView() {
        return view;
    }
}
