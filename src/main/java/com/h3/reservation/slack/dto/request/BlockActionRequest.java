package com.h3.reservation.slack.dto.request;

import com.h3.reservation.slack.dto.request.action.Action;

import java.util.List;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class BlockActionRequest {
    class View {
        private String privateMetadata;

        public View() {
        }

        public View(String privateMetadata) {
            this.privateMetadata = privateMetadata;
        }

        public String getPrivateMetadata() {
            return privateMetadata;
        }
    }
    private String triggerId;
    private String responseUrl;
    private List<Action> actions;
    private String type;
    private View view;

    public BlockActionRequest() {
    }

    public BlockActionRequest(String triggerId, String responseUrl, List<Action> actions, String type, View view) {
        this.triggerId = triggerId;
        this.responseUrl = responseUrl;
        this.actions = actions;
        this.type = type;
        this.view = view;
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

    public String getBlockId() {
        return actions.get(0).getBlockId();
    }

    public View getView() {
        return view;
    }

    public String getPrivateMetadata() {
        return view.getPrivateMetadata();
    }

    @Override
    public String toString() {
        return "BlockActionRequest{" +
            "triggerId='" + triggerId + '\'' +
            ", responseUrl='" + responseUrl + '\'' +
            ", actions=" + actions +
            ", type='" + type + '\'' +
            ", view=" + view +
            '}';
    }
}
