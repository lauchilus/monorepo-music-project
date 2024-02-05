package com.lauchilus.microservice.kafka;

import com.lauchilus.microservice.comments.CommentService;
import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Consumer {

    private final CommentService service;
    private final PostService postService;

    @KafkaListener(topics = "post-creation", groupId = "myGroup")
    public void consumePostMsg(PayloadPostTopic msg){
        Post post=postService.createPost(msg.getPostId(), msg.getUserId());
        log.info(String.format("Consuming the message from post-creation topic postID: %s:", post.getId()));
    }

    @KafkaListener(topics = "delete-comments", groupId = "myGroup")
    public void consumeDeleteMsg(String msg){
        postService.deletePost(msg);
        log.info("Consuming the message from delete-comments topic");
    }
}
