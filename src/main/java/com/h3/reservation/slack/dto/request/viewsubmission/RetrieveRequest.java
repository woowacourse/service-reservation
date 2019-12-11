package com.h3.reservation.slack.dto.request.viewsubmission;

public class RetrieveRequest {
    private String type;
    private RetrieveView view;

    public RetrieveRequest() {
    }

    public RetrieveRequest(String type, RetrieveView view) {
        this.type = type;
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public RetrieveView getView() {
        return view;
    }

    public String getCallbackId() {
        return view.getCallbackId();
    }
}
