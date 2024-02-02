package com.lauchilus.microservice.post;

import com.lauchilus.microservice.kafka.producer.PayloadPostTopic;
import com.lauchilus.microservice.post.dto.CreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreateDto dto){
        return new ResponseEntity<Post>(service.createPost(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String postId){
        return new ResponseEntity<Post>(service.getPost(postId),HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Post>> getAllUserPosts(@PathVariable String userId){
        return new ResponseEntity<List<Post>>(service.getAllPostFromUser(userId),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId){

        service.deletePost(postId);
        return new ResponseEntity<String>("Post Deleted",HttpStatus.OK);
    }
}
