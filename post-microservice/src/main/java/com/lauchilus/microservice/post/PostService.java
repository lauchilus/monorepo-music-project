package com.lauchilus.microservice.post;

import com.lauchilus.microservice.post.dto.CreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(CreateDto create){
    return postRepository.save(Post.builder()
            .songId(create.songId())
            .description(create.description())
            .userId(create.userId())
                    .likes(0L)
                    .likesList(new ArrayList<>())
            .build());
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
