package com.h3.reservation.slack.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationServiceTest {
    private static final String SIGNING_SECRET = "8f742231b10e8888abcd99yyyzzz85a5";
    private static final String SIGNATURE = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";
    private static final Clock CURRENT_TIME = Clock.fixed(Instant.ofEpochSecond(1531420618), ZoneId.of("UTC"));
    private static final String TIMESTAMP = "1531420618";
    private static final String REQUEST_BODY = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";

    private VerificationService verificationService;

    @BeforeEach
    void setUp() throws InvalidKeyException, NoSuchAlgorithmException {
        verificationService = new VerificationService(CURRENT_TIME, SIGNING_SECRET);
    }

    @Test
    void verificationTestRight() {
        assertThat(verificationService.verify(TIMESTAMP, REQUEST_BODY, SIGNATURE)).isTrue();
    }

    @Test
    void verificationTestTooLateRequest() throws NoSuchAlgorithmException, InvalidKeyException {
        verificationService = new VerificationService(
                Clock.offset(CURRENT_TIME, Duration.ofMinutes(5)),
                SIGNING_SECRET
        );
        assertThat(verificationService.verify(TIMESTAMP, REQUEST_BODY, SIGNATURE)).isFalse();
    }

    @Test
    void verificationTestDifferentBody() {
        String differentBody = "abc=314513";
        assertThat(verificationService.verify(TIMESTAMP, differentBody, SIGNATURE)).isFalse();
    }

    @Test
    void verificationTestWrongSignature() {
        String wrongSignature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b50";
        assertThat(verificationService.verify(TIMESTAMP, REQUEST_BODY, wrongSignature)).isFalse();
    }
}