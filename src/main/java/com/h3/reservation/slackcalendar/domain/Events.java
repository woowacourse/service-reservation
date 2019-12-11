package com.h3.reservation.slackcalendar.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
public class Events implements Iterable<Event> {
    private final List<Event> events;

    private Events(final List<Event> events) {
        this.events = events;
    }

    public static Events of(final List<Event> events) {
        return new Events(events);
    }

    public Stream<Event> stream() {
        return events.stream();
    }

    public boolean isEventEmpty() {
        return events.size() == 0;
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }

    @Override
    public void forEach(Consumer<? super Event> action) {
        events.forEach(action);
    }

    @Override
    public Spliterator<Event> spliterator() {
        return events.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Events events1 = (Events) o;

        return Objects.equals(events, events1.events);
    }

    @Override
    public int hashCode() {
        return events != null ? events.hashCode() : 0;
    }
}
