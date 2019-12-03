package com.h3.reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h3.reservation.slack.RequestDto;
import com.h3.reservation.slack.service.SlackService;
import net.minidev.json.JSONObject;
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

    private SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }


    @PostMapping("/slack/action")
    public ResponseEntity action(@RequestBody JSONObject req) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String type = req.getAsString("type");
        if ("url_verification".equals(type)) {
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(req.getAsString("challenge"));
        }
        if ("event_callback".equals(type)) {
            RequestDto requestDto = objectMapper.readValue(req.toString(), RequestDto.class);
            logger.error("ㅎㅇㅎㅇ: {}", requestDto.getChannel());
            slackService.action("https://slack.com/api/chat.postMessage", requestDto.getChannel());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}