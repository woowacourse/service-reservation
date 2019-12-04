package com.h3.reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.h3.reservation.slack.RequestType;
import com.h3.reservation.slack.dto.request.BlockActionRequest;
import com.h3.reservation.slack.dto.request.EventCallbackRequest;
import com.h3.reservation.slack.dto.request.VerificationRequest;
import com.h3.reservation.slack.service.SlackService;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author heebg
 * @version 1.0
 * @date 2019-12-02
 */
@RestController
public class BotController {
    private static final Logger logger = LoggerFactory.getLogger(BotController.class);

    private final SlackService service;
    private final ObjectMapper objectMapper;

    public BotController(SlackService service) {
        this.service = service;
        this.objectMapper = initObjectMapper();
    }

    @PostMapping("/slack/action")
    public ResponseEntity action(@RequestBody JSONObject req) throws JsonProcessingException {
        RequestType type = RequestType.valueOf(req.getAsString("type").toUpperCase());
        if (RequestType.URL_VERIFICATION.equals(type)) {
            return ResponseEntity.ok(service.verify(toDto(req, VerificationRequest.class)));
        }
        if (RequestType.EVENT_CALLBACK.equals(type)) {
            service.eventCallBack(toDto(req, EventCallbackRequest.class));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/slack/interaction", consumes = {"application/x-www-form-urlencoded"})
    public ResponseEntity interaction(@RequestParam Map<String, String> req, HttpServletRequest request) throws IOException, ParseException {
        printRequest(request);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(req.get("payload"));
        JSONObject jsonObj = (JSONObject) obj;
        logger.error("req : {}", req.toString());
        RequestType type = RequestType.valueOf(jsonObj.getAsString("type").toUpperCase());
        if (RequestType.BLOCK_ACTIONS.equals(type)) {
            service.viewModal(toDto(jsonObj, BlockActionRequest.class));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    private void printRequest(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(">> request body : " + body);
        }
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    private <T> T toDto(JSONObject object, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(object.toString(), clazz);
    }
}