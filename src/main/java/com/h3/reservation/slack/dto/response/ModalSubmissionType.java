package com.h3.reservation.slack.dto.response;

public enum ModalSubmissionType {
    RETRIEVE,
    RESERVE,
    CHANGE,
    CHANGE_REQUEST,
    CANCEL_REQUEST,
    RETRIEVE_RESULT,
    RESERVATION_RESULT,
    CHANGE_RESULT,
    CANCELLATION_RESULT;

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
