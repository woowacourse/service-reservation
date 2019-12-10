package com.h3.reservation.slack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.h3.reservation.slack.InitMenuType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.response.ModalUpdateResponse;
import com.h3.reservation.slack.dto.response.factory.InitResponseFactory;
import com.h3.reservation.slack.fragment.block.Block;
import com.h3.reservation.slack.fragment.block.ContextBlock;
import com.h3.reservation.slack.fragment.block.DividerBlock;
import com.h3.reservation.slack.fragment.block.SectionBlock;
import com.h3.reservation.slack.fragment.composition.text.MrkdwnText;
import com.h3.reservation.slack.fragment.composition.text.PlainText;
import com.h3.reservation.slack.fragment.view.ModalView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private final WebClient webClient = initWebClient();

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

    public ModalUpdateResponse updateModal() {
        return new ModalUpdateResponse(
                new ModalView(
                        new PlainText("조회하기"),
                        new PlainText("확인"),
                        generateDummyBlocks()
                )
        );
    }

    private WebClient initWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(config -> {
                    ObjectMapper objectMapper = (new ObjectMapper()).setPropertyNamingStrategy(
                            PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
                    );
                    config.customCodecs().encoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                }).build();
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

    private List<Block> generateDummyBlocks() {
        return Arrays.asList(
            new SectionBlock(
                new MrkdwnText("2019-12-05 12:10-14:10 회의실 예약 현황입니다.")
            ),
            new DividerBlock(),
            new ContextBlock(
                Collections.singletonList(
                    new MrkdwnText("*회의실 1*")
                )
            ),
            new DividerBlock(),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("버디"),
                    new PlainText("12:10-13:10")
                )
            ),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("희봉"),
                    new PlainText("13:10-14:10")
                )
            ),
            new DividerBlock(),
            new ContextBlock(
                Collections.singletonList(
                    new MrkdwnText("*회의실 2*")
                )
            ),
            new DividerBlock(),
            new SectionBlock(
                new MrkdwnText("*스터디*"),
                Arrays.asList(
                    new PlainText("닉"),
                    new PlainText("12:10-13:10")
                )
            ),
            new SectionBlock(
                new MrkdwnText("*프로젝트 회의*"),
                Arrays.asList(
                    new PlainText("도넛"),
                    new PlainText("13:10-14:10")
                )
            )
        );
    }
}
