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

    public String getDate() {
        return view.getValues().getDatepickerBlock().getSelectedDate();
    }

    public String getStartHour() {
        return view.getValues().getStartHourBlock().getStartHour().getSelectedOption().getValue();
    }

    public String getStartMinute() {
        return view.getValues().getStartMinuteBlock().getStartMinute().getSelectedOption().getValue();
    }

    public String getEndHour() {
        return view.getValues().getEndHourBlock().getEndHour().getSelectedOption().getValue();
    }

    public String getEndMinute() {
        return view.getValues().getEndMinuteBlock().getEndMinute().getSelectedOption().getValue();
    }
}
