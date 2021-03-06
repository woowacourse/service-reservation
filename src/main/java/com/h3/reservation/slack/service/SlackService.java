package com.h3.reservation.slack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h3.reservation.common.MeetingRoom;
import com.h3.reservation.common.ReservationDetails;
import com.h3.reservation.slack.EventType;
import com.h3.reservation.slack.InitMenuType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.dto.request.viewsubmission.CancelRequest;
import com.h3.reservation.slack.dto.request.viewsubmission.ChangeRequest;
import com.h3.reservation.slack.dto.request.viewsubmission.ReserveRequest;
import com.h3.reservation.slack.dto.request.viewsubmission.RetrieveRequest;
import com.h3.reservation.slack.dto.response.changeandcancel.CancelConfirmResponseFactory;
import com.h3.reservation.slack.dto.response.changeandcancel.CancelResultResponseFactory;
import com.h3.reservation.slack.dto.response.changeandcancel.ChangeAndCancelCandidateResponseFactory;
import com.h3.reservation.slack.dto.response.changeandcancel.ChangeInputResponseFactory;
import com.h3.reservation.slack.dto.response.changeandcancel.ChangeResultResponseFactory;
import com.h3.reservation.slack.dto.response.common.ModalResponse;
import com.h3.reservation.slack.dto.response.common.ModalUpdateResponse;
import com.h3.reservation.slack.dto.response.init.InitHomeTabResponseFactory;
import com.h3.reservation.slack.dto.response.init.InitResponseFactory;
import com.h3.reservation.slack.dto.response.reserve.ReserveAvailableMeetingResponseFactory;
import com.h3.reservation.slack.dto.response.reserve.ReserveInputResponseFactory;
import com.h3.reservation.slack.dto.response.reserve.ReserveResultResponseFactory;
import com.h3.reservation.slack.dto.response.retrieve.RetrieveResultResponseFactory;
import com.h3.reservation.slackcalendar.domain.DateTime;
import com.h3.reservation.slackcalendar.domain.Reservation;
import com.h3.reservation.slackcalendar.domain.Reservations;
import com.h3.reservation.slackcalendar.service.SlackCalendarService;
import com.h3.reservation.utils.BasicParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalTime;
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

    private final SlackCalendarService slackCalendarService;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public SlackService(SlackCalendarService slackCalendarService, ObjectMapper objectMapper) {
        this.slackCalendarService = slackCalendarService;
        this.objectMapper = objectMapper;
        this.webClient = initWebClient();
    }

    public String verify(VerificationRequest request) {
        return request.getChallenge();
    }

    public void showMenu(EventCallbackRequest request) {
        switch (EventType.of(request.getType())) {
            case APP_MENTION:
                send("/chat.postMessage", InitResponseFactory.of(request.getChannel()));
                break;
            case APP_HOME_OPENED:
                send("/views.publish", InitHomeTabResponseFactory.of(request.getUserId()));
        }
    }

    public void showModal(BlockActionRequest request) {
        if (request.getBlockId().equals("initial_block")) {
            send("/views.open", InitMenuType.of(request.getActionId()).apply(request.getTriggerId()));
            return;
        }
        if (request.getActionId().startsWith("request_reserve")) {
            send("/views.push", pushReserveModal(request));
            return;
        }
        if (request.getActionId().startsWith("request_change")) {
            send("/views.push", pushChangeModal(request));
            return;
        }
        if (request.getActionId().startsWith("request_cancel")) {
            send("/views.push", pushCancelModal(request));
        }
    }

    private ModalResponse pushReserveModal(BlockActionRequest request) {
        MeetingRoom meetingRoom = MeetingRoom.findByName(parseReservationId(request.getActionId()));
        List<String> tokens = BasicParser.parse(request.getPrivateMetadata(), "_");
        DateTime dateTime = DateTime.of(tokens.get(0), tokens.get(1), tokens.get(2));
        return ReserveInputResponseFactory.detail(request.getTriggerId(), dateTime, meetingRoom);
    }

    private ModalResponse pushChangeModal(BlockActionRequest request) {
        String reservationId = parseReservationId(request.getActionId());
        Reservation reservation = slackCalendarService.retrieveById(reservationId);
        return ChangeInputResponseFactory.of(request.getTriggerId(), reservation);
    }

    private String parseReservationId(String id) {
        String ID_REG = "_";
        int ID_INDEX = 2;
        return id.split(ID_REG)[ID_INDEX];
    }

    private ModalResponse pushCancelModal(BlockActionRequest request) {
        String reservationId = parseReservationId(request.getActionId());
        Reservation reservation = slackCalendarService.retrieveById(reservationId);
        return CancelConfirmResponseFactory.of(request.getTriggerId(), reservation);
    }

    public ModalUpdateResponse updateRetrieveResultModal(RetrieveRequest request) {
        DateTime retrieveRangeDateTime = DateTime.of(request.getDate()
            , generateLocalTime(request.getStartHour(), request.getStartMinute())
            , generateLocalTime(request.getEndHour(), request.getEndMinute()));
        Reservations reservations = slackCalendarService.retrieve(retrieveRangeDateTime);
        return RetrieveResultResponseFactory.of(retrieveRangeDateTime, reservations);
    }

    private LocalTime generateLocalTime(String hour, String minute) {
        return LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute));
    }

    public ModalUpdateResponse updateReserveAvailableMeetingRoomModal(ReserveRequest request) {
        DateTime dateTime = DateTime.of(request.getDate()
            , generateLocalTime(request.getStartHour(), request.getStartMinute())
            , generateLocalTime(request.getEndHour(), request.getEndMinute()));
        List<MeetingRoom> meetingRooms = slackCalendarService.retrieveAvailableMeetingRoom(dateTime);
        return ReserveAvailableMeetingResponseFactory.of(meetingRooms, dateTime);
    }

    public ModalUpdateResponse updateReserveResultModal(ReserveRequest request) {
        List<String> tokens = BasicParser.parse(request.getPrivateMetadata(), "_");
        DateTime dateTime = DateTime.of(tokens.get(0), tokens.get(1), tokens.get(2));
        ReservationDetails details = ReservationDetails.of(MeetingRoom.findByName(tokens.get(3)), request.getName(), request.getDescription());
        return ReserveResultResponseFactory.of(slackCalendarService.reserve(details, dateTime));
    }

    public ModalUpdateResponse updateChangeAndCancelCandidateModal(ChangeRequest request) {
        Reservations reservations = slackCalendarService.retrieve(request.getDate(), request.getName());
        return ChangeAndCancelCandidateResponseFactory.of(reservations);
    }

    public ModalUpdateResponse updateChangeResultModal(ReserveRequest request) {
        String reservationId = request.getPrivateMetadata();
        ReservationDetails details = ReservationDetails.of(
            MeetingRoom.findByName(request.getMeetingRoom()), request.getName(), request.getDescription()
        );
        DateTime dateTime = DateTime.of(request.getDate()
            , generateLocalTime(request.getStartHour(), request.getStartMinute())
            , generateLocalTime(request.getEndHour(), request.getEndMinute()));
        Reservation reservation = slackCalendarService.change(Reservation.of(reservationId, details, dateTime));
        return ChangeResultResponseFactory.of(reservation);
    }

    public ModalUpdateResponse updateCancelResultModal(CancelRequest request) {
        String reservationId = request.getPrivateMetadata();
        Reservation reservation = slackCalendarService.retrieveById(reservationId);
        slackCalendarService.cancel(reservation);
        return CancelResultResponseFactory.of(reservation);
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
