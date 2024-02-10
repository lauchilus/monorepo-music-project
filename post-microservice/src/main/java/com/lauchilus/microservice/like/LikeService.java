package com.lauchilus.microservice.like;

import com.lauchilus.microservice.kafka.producer.PostProducer;
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
    public final PostProducer producer;

    public List<String> getAllLikesFromPost(String postId){
        return repository.findAllByPostId(postId)
                .stream()
                .map(Like::getUserId)
                .collect(Collectors.toList());
    }

    public void addLike(String postId,String userId){
        if(repository.existsByPostId(postId)){
            Post post = postRepository.getReferenceById(postId);
            post.addLike();

            postRepository.save(post);
            Like like = Like.builder()
                    .userId(userId)
                    .post(post)
                    .build();
            System.out.println(post.getUserId());
            producer.SendNotificationLike(post.getUserId(),"New Like", "You recieved a like", userId,"like");
            repository.save(like);
        }else{
            throw new RuntimeException();
        }


    }

    public void deleteLike(String postId,String userId){
        Post post = postRepository.getReferenceById(postId);
        post.deleteLike();
        postRepository.save(post);
        Like like = repository.getReferenceByPostIdAndUserId(postId,userId);
        repository.delete(like);
    }
}
