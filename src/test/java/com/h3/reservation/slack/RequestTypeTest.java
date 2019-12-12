package com.h3.reservation.slack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-03
 */
class RequestTypeTest {
    @Test
    void valueOf() {
        RequestType requestType = RequestType.valueOf("event_callback".toUpperCase());
        assertEquals(requestType, RequestType.EVENT_CALLBACK);
    }
}