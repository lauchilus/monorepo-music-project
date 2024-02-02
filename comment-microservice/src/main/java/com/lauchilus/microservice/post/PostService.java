package com.lauchilus.microservice.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    public Post createPost(String postId,String userId){
        Post post = Post.builder()
                .userId(userId)
                .postId(postId)
                .comments(new ArrayList<>())
                .build();


        postRepository.insert(post);
        return post;
    }
}
