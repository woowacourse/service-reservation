package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.view.ModalView;

public class ChangeResponse {
    private String triggerId;
    private ModalView view;

    public ChangeResponse() {
    }

    public ChangeResponse(String triggerId, ModalView view) {
        this.triggerId = triggerId;
        this.view = view;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public ModalView getView() {
        return view;
    }
}
