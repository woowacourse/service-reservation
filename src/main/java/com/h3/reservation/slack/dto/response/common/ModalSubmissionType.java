package com.h3.reservation.slack.dto.response.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ModalSubmissionType {
    RETRIEVE_INPUT("retrieve_input"),
    RETRIEVE_RESULT("retrieve_result"),
    RESERVE_INPUT("reserve_input"),
    RESERVE_DATETIME_INPUT("reserve_datetime_input"),
    RESERVE_AVAILABLE_MEETINGROOM("reserve_available_meetingroom"),
    RESERVE_DETAIL_INPUT("reserve_detail_input"),
    RESERVE_RESULT("reserve_result"),
    CHANGE_AND_CANCEL_INPUT("change_and_cancel_input"),
    CHANGE_AND_CANCEL_CANDIDATE("change_and_cancel_candidate"),
    CHANGE_INPUT("change_input"),
    CHANGE_RESULT("change_result"),
    CANCEL_CONFIRM("cancel_confirm"),
    CANCEL_RESULT("cancel_result");

    @JsonValue
    private String type;

    ModalSubmissionType(String type) {
        this.type = type;
    }

    public static ModalSubmissionType of(String name) {
        return valueOf(name.toUpperCase());
    }

    public String getType() {
        return type;
    }
}
