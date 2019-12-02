package com.h3.reservation.controller;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
public class VerificationDto {
    private String token;
    private String challenge;
    private String type;

    public VerificationDto() {
    }

    public VerificationDto(String token, String challenge, String type) {
        this.token = token;
        this.challenge = challenge;
        this.type = type;
    }

    public String getChallenge() {
        return challenge;
    }
}
