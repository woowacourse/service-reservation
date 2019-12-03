package com.h3.reservation.slack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@Service
public class SlackService {
    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private static final String TOKEN = "xoxb-628979079522-857825073798-rZkXu22vIyUyVIgTZeKjwKkJ";
    private static final String AUTHORIZATION = "Bearer " + TOKEN;

    public void action(String url, String channel) {
        WebClient webClient = WebClient
            .builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
            .build();

        String response = webClient.post().body(BodyInserters.fromValue(generateBody(channel))).exchange().block().bodyToMono(String.class).block();
        logger.error("webclient response 응답 : {}", response);
    }

    private String generateBody(String channel) {
        return "{\n" +
            "\t\"channel\": \"" + channel + "\"," +
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
