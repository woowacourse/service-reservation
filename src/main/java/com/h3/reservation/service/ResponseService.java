package com.h3.reservation.service;

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
public class ResponseService {
    private static final String TOKEN = "xoxb-628979079522-857825073798-rZkXu22vIyUyVIgTZeKjwKkJ";
    private static final String AUTHORIZATION = "Bearer " + TOKEN;
    public void action(String url, Object body) {
        WebClient webClient = WebClient
            .builder()
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
            .build();

        webClient.post().body(BodyInserters.fromValue(body));
    }
}
