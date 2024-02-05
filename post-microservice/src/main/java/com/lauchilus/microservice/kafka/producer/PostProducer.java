package com.lauchilus.microservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostProducer {

    private final KafkaTemplate<String,PayloadPostTopic> kafkaTemplate;

    public void sendMessage(PayloadPostTopic msg){
        Message<PayloadPostTopic> payload = MessageBuilder
                .withPayload(msg)
                .setHeader(KafkaHeaders.TOPIC,"post-creation")
                .build();
        kafkaTemplate.send(payload);
    }

    public void sendMessageDeleteComments(String postId){
        Message<String> payload = MessageBuilder.withPayload(postId).setHeader(KafkaHeaders.TOPIC,"delete-comments")
                .build();
        kafkaTemplate.send(payload);
    }
}
