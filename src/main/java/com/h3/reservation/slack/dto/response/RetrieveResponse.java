package com.h3.reservation.slack.dto.response;

import com.h3.reservation.slack.fragment.view.ModalView;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-04
 */
public class RetrieveResponse {
    private String triggerId;
    private ModalView view;

    public RetrieveResponse() {
    }

    public RetrieveResponse(String triggerId, ModalView view) {
        this.triggerId = triggerId;
        this.view = view;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public ModalView getView() {
        return view;
    }
}
