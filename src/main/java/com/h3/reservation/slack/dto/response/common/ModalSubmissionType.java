package com.h3.reservation.slack.dto.response.common;

public enum ModalSubmissionType {
    RETRIEVE_INPUT,
    RESERVE_INPUT,
    CHANGE_AND_CANCEL_INPUT,
    CHANGE_INPUT,
    CANCEL_CONFIRM,
    RETRIEVE_RESULT,
    RESERVE_RESULT,
    CHANGE_RESULT,
    CANCEL_RESULT;

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
