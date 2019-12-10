package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.view.ModalView;

public class RetrieveModalUpdateResponse {
    private final String response_action = "update";
    private ModalView view;

    public RetrieveModalUpdateResponse() {
    }

    public RetrieveModalUpdateResponse(ModalView view) {
        this.view = view;
    }

    public String getResponse_action() {
        return response_action;
    }

    public ModalView getView() {
        return view;
    }
}
