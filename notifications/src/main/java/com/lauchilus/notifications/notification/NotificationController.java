package com.lauchilus.notifications.notification;

import com.lauchilus.notifications.config.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseMessagingService firebaseMessagingService;

    @PostMapping
    public String firebsaeNotification(@RequestBody NotificationMessage notificationMessage){
        return  firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }

}
