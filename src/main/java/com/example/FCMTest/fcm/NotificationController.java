package com.example.FCMTest.fcm;

import com.example.FCMTest.user.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity register(String token, String userId) throws Exception {
        notificationService.register(Long.valueOf(userId), token);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/test/{fbToken}")
    public void send(@PathVariable String fbToken) throws ExecutionException, InterruptedException {
        NotificationRequest notificationRequest = NotificationRequest.builder().title("test").message("tmg").token(fbToken).build();
        notificationService.sendNotification(notificationRequest);
    }

}
