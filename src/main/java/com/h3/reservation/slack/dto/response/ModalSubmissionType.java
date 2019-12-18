package com.h3.reservation.slack.dto.response;

public enum ModalSubmissionType {
    RETRIEVE_START,
    RESERVE_START,
    CHANGE_AND_CANCEL_START,
    CHANGE_SECOND_PUSH,
    CANCEL_SECOND_PUSH,
    RETRIEVE_FIRST_PUSH,
    RESERVE_FINISHED,
    CHANGE_FINISHED,
    CANCEL_FINISHED;

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }
}
