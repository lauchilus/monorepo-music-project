package com.lauchilus.microservice.post;

import com.lauchilus.microservice.comments.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;

    public Post createPost(String postId,String userId){
        Post post = Post.builder()
                .userId(userId)
                .postId(postId)
                .comments(new ArrayList<>())
                .build();


        postRepository.insert(post);
        return post;
    }

    public void deletePost(String postId){
        Post post = postRepository.findByPostId(postId);
        commentService.deleteAllCommentsForPost(post);
        postRepository.delete(post);
    }
}
