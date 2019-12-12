package com.h3.reservation.slack.dto.request;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
public class EventCallbackRequest {
    class Event {
        private String type;
        private String channel;
        private String user;

        public Event() {
        }

        public Event(String type, String channel, String user) {
            this.type = type;
            this.channel = channel;
            this.user = user;
        }

        public String getType() {
            return type;
        }

        public String getChannel() {
            return channel;
        }

        public String getUser() {
            return user;
        }
    }

    private Event event;

    public EventCallbackRequest() {
    }

    public EventCallbackRequest(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public String getType() {
        return event.getType();
    }

    public String getChannel() {
        return event.getChannel();
    }

    public String getUserId() {
        return event.getUser();
    }
}
