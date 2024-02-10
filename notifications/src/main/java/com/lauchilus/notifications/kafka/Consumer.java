package com.lauchilus.notifications.kafka;

import com.lauchilus.notifications.config.FirebaseMessagingService;
import com.lauchilus.notifications.notification.NotificationMessage;
import com.lauchilus.notifications.notification.NotificationService;
import com.lauchilus.notifications.userDevice.UserDevice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final NotificationService notificationService;
    private final FirebaseMessagingService firebaseMessagingService;

    @KafkaListener(topics = "follow-notification", groupId = "myGroup")
    public void consumeFollowMsg(NotificationMessage msg){
        notificationService.saveNotification(msg);
        msg.setRecipientToken(notificationService.getUserDeviceToken(msg.getRecipientToken()));
        firebaseMessagingService.sendNotificationByToken(msg);
        log.info("Consuming the message from follow-notification");
    }

    @KafkaListener(topics = "like-notification", groupId = "myGroup")
    public void consumeLikeMsg(NotificationMessage msg){
        notificationService.saveNotification(msg);
        msg.setRecipientToken(notificationService.getUserDeviceToken(msg.getRecipientToken()));
        firebaseMessagingService.sendNotificationByToken(msg);
        log.info("Consuming the message from like-notification topic");
    }

    @KafkaListener(topics = "comment-notification", groupId = "myGroup")
    public void consumeCommentMsg(NotificationMessage msg){
        notificationService.saveNotification(msg);
        msg.setRecipientToken(notificationService.getUserDeviceToken(msg.getRecipientToken()));
        firebaseMessagingService.sendNotificationByToken(msg);
        log.info("Consuming the message from comment-comments topic");
    }

}
