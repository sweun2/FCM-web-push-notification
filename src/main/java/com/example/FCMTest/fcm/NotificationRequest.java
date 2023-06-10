package com.example.FCMTest.fcm;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
public class NotificationRequest {
    private String title;
    private String token;
    private String message;
}
