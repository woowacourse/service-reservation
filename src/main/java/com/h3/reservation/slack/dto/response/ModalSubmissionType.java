package com.h3.reservation.slack.dto.response;

public enum ModalSubmissionType {
    RETRIEVE,
    RESERVE,
    CHANGE,
    CHANGE_REQUEST,
    CANCEL_REQUEST,
    CHANGE_RESULT,
    CANCELLATION_RESULT;

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
