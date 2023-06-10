package com.example.FCMTest.fcm;

import com.example.FCMTest.user.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class NotificationService {


    private final Map<Long, String> tokenMap = new HashMap<>();


    public void register(final Long userId, final String token) {
        tokenMap.put(userId, token);
    }

    private void createReceiveNotification(String token) throws ExecutionException, InterruptedException {

            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .title("POST RECEIVED")
                    .token(token)
                    .message("test")
                    .build();
            sendNotification(notificationRequest);

    }
    public void sendNotification(NotificationRequest notificationRequest) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
                                notificationRequest.getMessage()))
                        .build())
                .build();
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    }
}
