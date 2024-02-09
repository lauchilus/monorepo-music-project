package com.lauchilus.microservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    private final KafkaTemplate<String,NotificationMessage> kafkaTemplateNotification;

    public void SendNotificationLike(String recipientId,String title, String body, String username){
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .body(body)
                .recipientToken(recipientId)
                .title(title)
                .username(username)
                .build();

        Message<NotificationMessage> message = MessageBuilder.withPayload(notificationMessage)
                .setHeader(KafkaHeaders.TOPIC,"comment-notification")
                .build();
        kafkaTemplateNotification.send(message);
    }

}
