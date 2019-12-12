package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.view.ModalView;

public class ReserveResponse {
    private String triggerId;
    private ModalView view;

    public ReserveResponse() {
    }

    public ReserveResponse(String triggerId, ModalView view) {
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
