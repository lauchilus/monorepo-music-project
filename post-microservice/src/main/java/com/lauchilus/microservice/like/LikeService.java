package com.lauchilus.microservice.like;

import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.AlreadyBoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {

    public final LikeRepository repository;
    public final PostRepository postRepository;

    public List<String> getAllLikesFromPost(String postId){
        return repository.findAllByPostId(postId)
                .stream()
                .map(Like::getUserId)
                .collect(Collectors.toList());
    }

    public void addLike(String postId,String userId){
        if(repository.existsByPostIdAndUserId(postId,userId)){
            throw new RuntimeException();
        }
        Post post = postRepository.getReferenceById(postId);
        post.addLike();

        postRepository.save(post);
        Like like = Like.builder()
                .userId(userId)
                .post(post)
                .build();
        repository.save(like);
    }

    public void deleteLike(String postId,String userId){
        Post post = postRepository.getReferenceById(postId);
        post.deleteLike();
        postRepository.save(post);
        Like like = repository.getReferenceByPostIdAndUserId(postId,userId);
        repository.delete(like);
    }
}
