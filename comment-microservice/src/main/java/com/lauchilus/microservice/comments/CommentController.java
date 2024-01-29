package com.lauchilus.microservice.comments;

import com.lauchilus.microservice.comments.dto.AddCommentDto;
import com.lauchilus.microservice.comments.dto.ResponseComments;
import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;
    private final PostService postService;
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody AddCommentDto commentDto){
        return new ResponseEntity<Comment>(service.addComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<ResponseComments>> getAllPostComments(@PathVariable String postId){
        return new ResponseEntity<List<ResponseComments>>(service.getAllPostComments(postId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable String commentId){
        service.deleteComment(commentId);
        return new ResponseEntity<String>("Comment Deleted",HttpStatus.OK);
    }

    @PostMapping("/addPost")
    public ResponseEntity<Post> dasdad(){
    return new ResponseEntity<>(postService.createPost("260add0e-2880-42df-9813-15175e5c1d93","userTest1223jgh"),HttpStatus.OK);
    }
}
