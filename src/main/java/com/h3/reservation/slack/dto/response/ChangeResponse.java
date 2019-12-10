package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.view.ModalView;

public class ChangeResponse {
    private String trigger_id;
    private ModalView view;

    public ChangeResponse() {
    }

    public ChangeResponse(String trigger_id, ModalView view) {
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
