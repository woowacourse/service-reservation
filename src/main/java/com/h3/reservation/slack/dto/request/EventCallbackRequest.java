package com.h3.reservation.slack.dto.request;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
public class EventCallbackRequest {
    class Event {
        private String channel;

        public Event() {
        }

        public Event(String channel) {
            this.channel = channel;
        }

        public String getChannel() {
            return channel;
        }
    }

    private Event event;

    public EventCallbackRequest() {
    }

    public EventCallbackRequest(Event event) {
        this.event = event;
    }

    public String getChannel() {
        return event.getChannel();
    }

    public Event getEvent() {
        return event;
    }
}
