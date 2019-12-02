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
        String body = generateBody(req.getEvent().getChannel());
        responseService.action("https://slack.com/api/chat.postMessage", body);
        return ResponseEntity.ok().build();
    }

    private String generateBody(String channel) {
        return "{\n" +
            "\t\"channel\": " + channel + "," +
            "\t\"blocks\": [\n" +
            "\t{\n" +
            "\t\t\"type\": \"actions\",\n" +
            "\t\t\"elements\": [\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \":spiral_calendar_pad: 전체 조회\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"value\": \"retrieve\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \":pushpin: 회의실 예약\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"value\": \"reserve\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \":file_folder: 예약 확인\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"value\": \"confirm\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \":memo: 예약 변경\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"value\": \"change\"\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \":scissors: 예약 취소\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"value\": \"cancel\"\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "]\n" +
            "}";
    }
}