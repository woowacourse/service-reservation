package com.h3.reservation.slack.dto.request.action;


import com.h3.reservation.slack.fragment.composition.text.Text;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Action {
    private String actionId;
    private String blockId;
    private Text text;
    private String value;
    private String actionTs;

    public Action() {
    }

    public Action(String actionId, String blockId, Text text, String value, String actionTs) {
        this.actionId = actionId;
        this.blockId = blockId;
        this.text = text;
        this.value = value;
        this.actionTs = actionTs;
    }

    public String getActionId() {
        return actionId;
    }

    public String getBlockId() {
        return blockId;
    }

    public Text getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public String getActionTs() {
        return actionTs;
    }

    @Override
    public String toString() {
        return "Action{" +
            "action_id='" + actionId + '\'' +
            ", block_id='" + blockId + '\'' +
            ", text=" + text +
            ", value='" + value + '\'' +
            ", action_ts='" + actionTs + '\'' +
            '}';
    }
}
