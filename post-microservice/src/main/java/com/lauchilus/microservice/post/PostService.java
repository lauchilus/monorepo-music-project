package com.lauchilus.microservice.post;

import com.lauchilus.microservice.kafka.producer.PayloadPostTopic;
import com.lauchilus.microservice.kafka.producer.PostProducer;
import com.lauchilus.microservice.post.dto.CreateDto;
import com.lauchilus.microservice.post.dto.GetPostDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final PostProducer producer;

    private final RestTemplate restTemplate;

    private String trackUrl = "http://localhost:8094/api/v1/search/details?id=";

    @Transactional
    public Post createPost(CreateDto create,String uid){
        Post post = postRepository.save(Post.builder()
                .songId(create.songId())
                .description(create.description())
                .userId(uid)
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

    public GetPostDto getPost(String postId){
        Post post = postRepository.getReferenceById(postId);
        String q = String.format(trackUrl+post.getSongId());
        SpotifyTrackInfo track = restTemplate.getForObject(q, SpotifyTrackInfo.class);
        GetPostDto response = new GetPostDto(post.getId(),post.getUserId(),post.getDescription(),post.getLikes(),track);
        return response ;
    }

    public List<GetPostDto> getAllPostFromUser(String userId){
        List<Post> listPost = postRepository.findAllByUserId(userId);
        List<GetPostDto> response = new ArrayList<>();
        for(Post p : listPost){
            String q = String.format(trackUrl+p.getSongId());
            SpotifyTrackInfo track = restTemplate.getForObject(q, SpotifyTrackInfo.class);
            GetPostDto r = new GetPostDto(p.getId(),p.getUserId(),p.getDescription(),p.getLikes(),track);
            response.add(r);

        }
        return response;
    }

    public void deletePost(String postId){
        producer.sendMessageDeleteComments(postId);
        postRepository.deleteById(postId);
    }
}
