package com.h3.reservation.slack.dto.request;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
public class VerificationRequest {
    private String token;
    private String challenge;
    private String type;

    public VerificationRequest() {
    }

    public VerificationRequest(String token, String challenge, String type) {
        this.token = token;
        this.challenge = challenge;
        this.type = type;
    }

    public String getChallenge() {
        return challenge;
    }
}
