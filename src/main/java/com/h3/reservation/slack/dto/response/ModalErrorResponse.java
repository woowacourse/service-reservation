package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.error.Errors;

public class ModalErrorResponse {
    private final ModalActionType responseAction = ModalActionType.ERRORS;
    private Errors errors;

    public ModalErrorResponse() {
    }

    public ModalErrorResponse(Errors errors) {
        this.errors = errors;
    }

    public ModalActionType getResponseAction() {
        return responseAction;
    }

    public Errors getErrors() {
        return errors;
    }
}
