package com.h3.reservation.slack.dto.request;

import com.h3.reservation.slack.dto.request.action.Action;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class BlockActionRequest {
    private String triggerId;
    private String responseUrl;
    private List<Action> actions;
    private String type;

    public BlockActionRequest() {
    }

    public BlockActionRequest(String triggerId, String responseUrl, List<Action> actions, String type) {
        this.triggerId = triggerId;
        this.responseUrl = responseUrl;
        this.actions = actions;
        this.type = type;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public List<Action> getActions() {
        return actions;
    }

    public String getType() {
        return type;
    }

    public String getActionId() {
        return actions.get(0).getActionId();
    }

    @Override
    public String toString() {
        return "BlockActionRequest{" +
            "trigger_id='" + triggerId + '\'' +
            ", response_url='" + responseUrl + '\'' +
            ", actions=" + actions +
            ", type='" + type + '\'' +
            '}';
    }
}
