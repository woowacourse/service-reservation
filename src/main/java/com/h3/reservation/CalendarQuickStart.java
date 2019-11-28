package com.h3.reservation;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

public class CalendarQuickStart {
    private static final Logger log = LoggerFactory.getLogger(CalendarQuickStart.class);
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
//    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final Set<String> SCOPES = CalendarScopes.all();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static final String CALENDER_ID = "lrcmnt5smcksr6om6leb4qjfds@group.calendar.google.com";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickStart.class.getResourceAsStream(CREDENTIALS_FILE_PATH); //credential.json 파일 가져옴
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }

        //credentials.json 의 정보를 객체로..?
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request. -> 유저 권한 요청 실행
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service. 캘린더 생성
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        //회의실 전체 조회
        Events events = service.events().list(CALENDER_ID).execute();
        for (Event event : events.getItems()) {
            log.info("만든이 - {}, 제목 - {}, 설명 - {}, 위치 - {}, 시작:{}, 끝:{}",
                    event.getCreator(), event.getSummary(), event.getDescription(), event.getLocation(), event.getStart(), event.getEnd());
        }

        //회의실 등록
        makeEvent();

    }

    public static Event addEvent(Event event) throws IOException, GeneralSecurityException, GoogleJsonResponseException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return service.events().insert(CALENDER_ID, event).execute();
    }

    /**
     * 제목, 시작시간, 종료시간 입력해서 이벤트를 생성하는 메소드
     */
    public static Event makeEvent() throws IOException {
        Event event = new Event()
                .setSummary("회의실4/스터디/버디")
                .setLocation("잠실 본동")
                .setDescription("스터디할거에여");

        DateTime startDateTime = new DateTime(new Date(), TimeZone.getDefault());

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul");

        event.setStart(start);

        DateTime endDateTime = new DateTime(new Date(), TimeZone.getDefault());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul");

        event.setEnd(end);

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("popup").setMinutes(10),
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));

        event.setReminders(reminders);

        try {
            event = CalendarQuickStart.addEvent(event);
            log.info("EVENT 생성 완료" + event.toPrettyString());
        } catch (Exception ex) {
            log.info("Calendar Exception in insert");
        }

        return event;
    }
}
