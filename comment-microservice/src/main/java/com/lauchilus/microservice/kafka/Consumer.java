package com.lauchilus.microservice.kafka;

import com.lauchilus.microservice.comments.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final CommentService service;

    @KafkaListener(topics = "post-creation", groupId = "myGroup")
    public void consumePostMsg(PayloadPostTopic msg){
        log.info(String.format("Consuming the message from post-creation topic postID: %s:",msg.toString()));

    }
}
