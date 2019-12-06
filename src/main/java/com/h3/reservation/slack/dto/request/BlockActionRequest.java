package com.h3.reservation.slack.dto.request;

import com.h3.reservation.slack.dto.request.action.Action;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class BlockActionRequest {
    private String trigger_id;
    private String response_url;
    private List<Action> actions;
    private String type;

    public BlockActionRequest() {
    }

    public BlockActionRequest(String trigger_id, String response_url, List<Action> actions, String type) {
        this.trigger_id = trigger_id;
        this.response_url = response_url;
        this.actions = actions;
        this.type = type;
    }

    public String getTrigger_id() {
        return trigger_id;
    }

    public String getResponse_url() {
        return response_url;
    }

    public List<Action> getActions() {
        return actions;
    }

    public String getType() {
        return type;
    }

    public String getAction_id() {
        return actions.get(0).getAction_id();
    }

    @Override
    public String toString() {
        return "BlockActionRequest{" +
            "trigger_id='" + trigger_id + '\'' +
            ", response_url='" + response_url + '\'' +
            ", actions=" + actions +
            ", type='" + type + '\'' +
            '}';
    }
}
