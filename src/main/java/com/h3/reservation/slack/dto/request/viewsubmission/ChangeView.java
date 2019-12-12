package com.h3.reservation.slack.dto.request.viewsubmission;

import com.h3.reservation.slack.dto.request.viewsubmission.values.ChangeValues;

public class ChangeView {
    class State {
        private ChangeValues values;

        public State() {
        }

        public State(ChangeValues values) {
            this.values = values;
        }

        public ChangeValues getValues() {
            return values;
        }
    }

    private String callbackId;
    private State state;

    public ChangeView() {
    }

    public ChangeView(String callbackId, State state) {
        this.callbackId = callbackId;
        this.state = state;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public State getState() {
        return state;
    }

    public ChangeValues getValues() {
        return state.getValues();
    }
}
