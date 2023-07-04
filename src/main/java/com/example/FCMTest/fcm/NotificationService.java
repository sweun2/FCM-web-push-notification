package com.example.FCMTest.fcm;

import com.example.FCMTest.user.User;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class NotificationService {
    private final Map<Long, String> tokenMap = new HashMap<>();
    private final List<String> tokenList = new ArrayList<>();

    public void register(final Long userId, final String token) throws FirebaseMessagingException {
        tokenMap.put(userId, token);
        tokenList.add(token);
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                tokenList, "DEFAULT");
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
                .setTopic("DEFAULT")
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
                                notificationRequest.getMessage()))
                        .build())
                .build();
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    }
}
