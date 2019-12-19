package com.h3.reservation.slack.dto.request.viewsubmission;

public class ReserveRequest {
    private String type;
    private ReserveView view;

    public ReserveRequest() {
    }

    public ReserveRequest(String type, ReserveView view) {
        this.type = type;
        this.view = view;
    }

    public String getType() {
        return type;
    }

    public ReserveView getView() {
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

    public String getMeetingRoom() {
        return view.getValues().getMeetingRoomBlock().getMeetingRoom().getSelectedOption().getValue();
    }

    public String getDescription() {
        return view.getValues().getDescriptionBlock().getValue();
    }

    public String getName() {
        return view.getValues().getNameBlock().getValue();
    }

    public String getPrivateMetadata() {
        return view.getPrivateMetadata();
    }
}
