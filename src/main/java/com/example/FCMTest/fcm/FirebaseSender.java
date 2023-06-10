package com.example.FCMTest.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class FirebaseSender {
    private final RestTemplate restTemplate;
    private final String CONFIG_PATH = "firebase/firebase-key.json";
    // 토큰 발급 URL
    private final String AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";
    // 엔드포인트 URL
    private final String SEND_URL = "https://fcm.googleapis.com/v1/projects/fcmtest-b8dc9/messages:send";
    private String getAccessToken() throws IOException {
        // 토큰 발급
        GoogleCredentials googleCredentials = GoogleCredentials .fromStream(new ClassPathResource(CONFIG_PATH).getInputStream()).createScoped(List.of(AUTH_URL));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
    @Getter
    @Builder
    private static class PushPayload
    { // API 호출시 Body에 보낼 객체
        private boolean validate_only;
        private Message message;
        @Getter @Builder
        private static class Message {
            private String token;
            private Notification notification;
        }
        @Getter @Builder private static class Notification {
            private String title;
            private String body;
            private String image;
        }
    }
    private PushPayload getBody(String to, String title, String body) {
        return PushPayload.builder()
                .validate_only(false)
                .message(PushPayload.Message.builder()
                        .token(to)
                        .notification(PushPayload.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build())
                        .build())
                .build();
    }
    public void pushBrowserSend(String to, String title, String body) throws IOException {
        // 발송 API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken());
        final HttpEntity<Object> entity = new HttpEntity<>(getBody(to, title, body), (MultiValueMap<String, String>) headers);
        final ResponseEntity<String> response = restTemplate.exchange(SEND_URL, HttpMethod.POST, entity, String.class);
        final HttpStatus status = (HttpStatus) response.getStatusCode(); final String responseBody = response.getBody();
        if (status.equals(HttpStatus.OK)) {
            // 발송 API 호출 성공
            }
            else {
                // 발송 API 호출 실패 } }
            }
        }


}
