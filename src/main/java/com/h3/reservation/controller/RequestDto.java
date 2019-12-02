package com.h3.reservation.controller;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
public class RequestDto {
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

    public RequestDto() {
    }

    public RequestDto(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
