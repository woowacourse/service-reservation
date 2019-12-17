package com.h3.reservation.slack.dto.response;

public enum ModalSubmissionType {
    RETRIEVE,
    RESERVE,
    CHANGE,
    CHANGE_REQUEST,
    CANCEL_REQUEST;

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
