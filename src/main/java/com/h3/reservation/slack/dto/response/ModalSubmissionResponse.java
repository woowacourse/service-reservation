package com.h3.reservation.slack.dto.response;

public class ModalSubmissionResponse {
    private ModalActionType responseAction;

    public ModalSubmissionResponse() {
    }

    public ModalSubmissionResponse(ModalActionType responseAction) {
        this.responseAction = responseAction;
    }

    public ModalActionType getResponseAction() {
        return responseAction;
    }
}
