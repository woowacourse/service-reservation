package com.h3.reservation.slack.dto.response.common;

import com.h3.reservation.slack.fragment.view.ModalView;

public class ModalUpdateResponse extends ModalSubmissionResponse {
    private ModalView view;

    public ModalUpdateResponse() {
        super(ModalActionType.UPDATE);
    }

    public ModalUpdateResponse(ModalView view) {
        super(ModalActionType.UPDATE);
        this.view = view;
    }

    public ModalView getView() {
        return view;
    }
}
