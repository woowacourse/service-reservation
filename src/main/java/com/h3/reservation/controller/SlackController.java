package com.h3.reservation.controller;

import com.h3.reservation.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private ResponseService responseService;

    public SlackController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping("/slack/action")
    public ResponseEntity<Void> action(@RequestBody RequestDto req) {
        logger.error("ㅎㅇㅎㅇ: {}", req.getEvent().getChannel());
        responseService.action("https://slack.com/api/chat.postMessage", req.getEvent().getChannel());
        return ResponseEntity.ok().build();
    }
}