package com.lauchilus.microservice.post;

import com.lauchilus.microservice.kafka.producer.PayloadPostTopic;
import com.lauchilus.microservice.kafka.producer.PostProducer;
import com.lauchilus.microservice.post.dto.CreateDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostProducer producer;

    @Transactional
    public Post createPost(CreateDto create){
        Post post = postRepository.save(Post.builder()
                .songId(create.songId())
                .description(create.description())
                .userId(create.userId())
                .likes(0L)
                .likesList(new ArrayList<>())
                .build());
        PayloadPostTopic payload = PayloadPostTopic.builder()
                .postId(post.getId())
                .userId(post.getUserId())
                .build();
    producer.sendMessage(payload);
    return post;
    }

    public Post getPost(String postId){
        Post post = postRepository.getReferenceById(postId);
        return post ;
    }

    public List<Post> getAllPostFromUser(String userId){
        return postRepository.findAllByUserId(userId);
    }

    public void deletePost(String postId){
        postRepository.deleteById(postId);
    }
}
