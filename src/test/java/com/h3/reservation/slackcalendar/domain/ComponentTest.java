package com.h3.reservation.slackcalendar.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-11
 */
class ComponentTest {
    @Test
    void constructor() {
        String room = "회의실1";
        String booker = "희봉";
        String purpose = "프로젝트 회의";
        Component component = Component.of(room, booker, purpose);

        assertEquals(component, Component.of(room, booker, purpose));
    }
}