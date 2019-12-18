package com.h3.reservation.slack.service;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;

public class VerificationService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationService.class);

    private static final String VERSION = "v0";
    private static final String ALGORITHM_TYPE = "HmacSHA256";
    private static final long MAX_TIME_DIFF = 3 * 60;

    private final Clock clock;
    private final Mac mac;

    public VerificationService(Clock clock, String signingSecret) throws InvalidKeyException, NoSuchAlgorithmException {
        this.clock = clock;
        this.mac = Mac.getInstance(ALGORITHM_TYPE);
        this.mac.init(new SecretKeySpec(signingSecret.getBytes(), ALGORITHM_TYPE));
    }

    public boolean verify(String requestTime, String requestBody, String signature) {
        if (Math.abs(clock.instant().getEpochSecond() - Long.parseLong(requestTime)) > MAX_TIME_DIFF) {
            return false;
        }
        String result = VERSION + "="
                + Hex.encodeHexString(mac.doFinal(String.join(":", VERSION, requestTime, requestBody).getBytes()));
        return result.equals(signature);
    }
}
