package com.h3.reservation.slack.dto.request.viewsubmission;

public class ChangeRequest {
    private String type;
    private ChangeView view;

    public ChangeRequest(String type, ChangeView view) {
        this.type = type;
        this.view = view;
    }

    public ChangeRequest() {
    }

    public String getType() {
        return type;
    }

    public ChangeView getView() {
        return view;
    }

    public String getCallbackId() {
        return view.getCallbackId();
    }

    public String getDate() {
        return view.getValues().getDatepickerBlock().getSelectedDate();
    }

    public String getName() {
        return view.getValues().getNameBlock().getValue();
    }
}
