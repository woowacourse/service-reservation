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
public class Reservations implements Iterable<Reservation> {
    private final List<Reservation> reservations;

    private Reservations(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public static Reservations of(final List<Reservation> reservations) {
        return new Reservations(reservations);
    }

    public Stream<Reservation> stream() {
        return reservations.stream();
    }

    public boolean isEventEmpty() {
        return reservations.size() == 0;
    }

    @Override
    public Iterator<Reservation> iterator() {
        return reservations.iterator();
    }

    @Override
    public void forEach(Consumer<? super Reservation> action) {
        reservations.forEach(action);
    }

    @Override
    public Spliterator<Reservation> spliterator() {
        return reservations.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservations reservations1 = (Reservations) o;

        return Objects.equals(reservations, reservations1.reservations);
    }

    @Override
    public int hashCode() {
        return reservations != null ? reservations.hashCode() : 0;
    }
}
