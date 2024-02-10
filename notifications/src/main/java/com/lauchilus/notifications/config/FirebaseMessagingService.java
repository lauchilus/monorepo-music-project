package com.lauchilus.notifications.config;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lauchilus.notifications.notification.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(NotificationMessage notificationMessage){
        Notification notification = Notification.builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage("https://media.discordapp.net/attachments/611022942004051986/852654700926206022/FaceApp_1623357540197.jpg?ex=65d7b712&is=65c54212&hm=92e1c7d9f13d3f41f8986e0e8a050f97280dc7e74a342abbd8bb1ecbfb55768a&=&format=webp&width=266&height=473")
                .build();

        Message message = Message.builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Success Sending Notification";
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Error sending notification";
        }

    }
}
