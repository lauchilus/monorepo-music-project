package com.lauchilus.microservice.like;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/post/likes")
@RequiredArgsConstructor
public class LikeController {

    public final LikeService service;

    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable String postId, @RequestParam String userId){
        service.addLike(postId,userId);
        return new ResponseEntity<String>("like added", HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<String>> getLikesFromPost(@PathVariable String postId){
        return new ResponseEntity<List<String>>(service.getAllLikesFromPost(postId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> removeLike(@PathVariable String postId,@RequestParam String userId){
        service.deleteLike(postId,userId);
        return new ResponseEntity<String>("Like removed",HttpStatus.OK);
    }
}
