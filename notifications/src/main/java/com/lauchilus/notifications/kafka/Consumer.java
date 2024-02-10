package com.lauchilus.notifications.kafka;

import com.lauchilus.notifications.notification.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {
    //TODO CREATE TOPICS IN KAFKA

    @KafkaListener(topics = "follow-notification", groupId = "myGroup")
    public void consumeFollowMsg(NotificationMessage msg){
        //TODO create notification and use FirebaseMessagingService
        //TODO TEST
        log.info(String.format("Consuming the message from follow-notification topic postID: %s:", null));
    }

    @KafkaListener(topics = "like-notification", groupId = "myGroup")
    public void consumeLikeMsg(NotificationMessage msg){
        //TODO create notification and use FirebaseMessagingService
        log.info("Consuming the message from like-notification topic");
    }

    @KafkaListener(topics = "comment-notification", groupId = "myGroup")
    public void consumeCommentMsg(NotificationMessage msg){
        //TODO create notification and use FirebaseMessagingService
        log.info("Consuming the message from comment-comments topic");
    }

}
