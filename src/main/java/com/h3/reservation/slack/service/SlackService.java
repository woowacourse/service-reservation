package com.h3.reservation.slack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h3.reservation.slack.InitMenuType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.RetrieveModalUpdateResponse;
import com.h3.reservation.slack.dto.response.factory.InitResponseFactory;
import com.h3.reservation.slack.dto.response.factory.RetrieveModalUpdateResponseFactory;
import com.h3.reservation.slackcalendar.domain.EventDateTime;
import com.h3.reservation.slackcalendar.domain.Events;
import com.h3.reservation.slackcalendar.service.SlackCalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@Service
public class SlackService {
    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    private static final String BASE_URL = "https://slack.com/api";
    private static final String TOKEN = "Bearer " + System.getenv("BOT_TOKEN");

    private final SlackCalendarService slackCalendarService;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public SlackService(SlackCalendarService slackCalendarService, ObjectMapper objectMapper) {
        this.slackCalendarService = slackCalendarService;
        this.objectMapper = objectMapper;
        this.webClient = initWebClient();
    }

    public String verify(VerificationRequest dto) {
        return dto.getChallenge();
    }

    public void showMenu(EventCallbackRequest dto) {
        String postUrl = "/chat.postMessage";
        send(postUrl, InitResponseFactory.of(dto.getChannel()));
    }

    public void showModal(BlockActionRequest dto) {
        String postUrl = "/views.open";
        send(postUrl, InitMenuType.of(dto.getActionId()).apply(dto.getTriggerId()));
    }

    public RetrieveModalUpdateResponse updateModal() {
        EventDateTime retrieveRangeDateTime = EventDateTime.of("2019-12-10", "10:00", "18:00");
        Events events = slackCalendarService.retrieve(retrieveRangeDateTime);
        return RetrieveModalUpdateResponseFactory.of(retrieveRangeDateTime, events);
    }

    private WebClient initWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(config ->
                config.customCodecs().encoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
            ).build();
        return WebClient.builder()
            .exchangeStrategies(strategies)
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.AUTHORIZATION, TOKEN)
            .build();
    }

    private void send(String url, Object dto) {
        String response = webClient.post()
            .uri(url)
            .body(BodyInserters.fromValue(dto))
            .exchange().block().bodyToMono(String.class)
            .block();
        logger.debug("WebClient Response: {}", response);
    }
}
