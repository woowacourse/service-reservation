package com.h3.reservation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@RestController
public class SlackController {
    @PostMapping("/slack/action")
    public ResponseEntity<String> verify(@RequestBody VerificationDto dto) {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(dto.getChallenge());
    }
}