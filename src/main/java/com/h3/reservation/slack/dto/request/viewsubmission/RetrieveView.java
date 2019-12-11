package com.h3.reservation.slack.dto.request.viewsubmission;

import com.h3.reservation.slack.dto.request.viewsubmission.values.RetrieveValues;

public class RetrieveView {
    class State {
        private RetrieveValues values;

        public State() {
        }

        public State(RetrieveValues values) {
            this.values = values;
        }

        public RetrieveValues getValues() {
            return values;
        }
    }

    private String callback_id;
    private State state;

    public RetrieveView() {
    }

    public RetrieveView(String callback_id, State state) {
        this.callback_id = callback_id;
        this.state = state;
    }

    public String getCallback_id() {
        return callback_id;
    }

    public State getState() {
        return state;
    }
}
