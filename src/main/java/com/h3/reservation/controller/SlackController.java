package com.h3.reservation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SlackController.class);

    @PostMapping("/slack/action")
    public ResponseEntity<Void> action(@RequestBody RequestDto req) {
        logger.error("ㅎㅇㅎㅇ: {}", req.getEvent().getChannel());
        return ResponseEntity.ok().build();
    }
}