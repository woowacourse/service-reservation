package com.h3.reservation.slack.dto.response;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
public class Action {
    private String action_id;
    private String block_id;
    private Text text;
    private String value;
    private String action_ts;

    public Action() {
    }

    public Action(String action_id, String block_id, Text text, String value, String action_ts) {
        this.action_id = action_id;
        this.block_id = block_id;
        this.text = text;
        this.value = value;
        this.action_ts = action_ts;
    }

    public String getAction_id() {
        return action_id;
    }

    public String getBlock_id() {
        return block_id;
    }

    public Text getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public String getAction_ts() {
        return action_ts;
    }

    @Override
    public String toString() {
        return "Action{" +
            "action_id='" + action_id + '\'' +
            ", block_id='" + block_id + '\'' +
            ", text=" + text +
            ", value='" + value + '\'' +
            ", action_ts='" + action_ts + '\'' +
            '}';
    }
}
