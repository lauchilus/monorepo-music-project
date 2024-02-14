package com.lauchilus.microservice.like;

import com.lauchilus.microservice.kafka.producer.PostProducer;
import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostProducer postProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        likeService = new LikeService(likeRepository,postRepository,postProducer);
    }

    @Test
    void getAllLikesFromPost__shouldReturnListOfUserids() {
        List<Like> likesList = new ArrayList<>();
        List<String> likesStringList = new ArrayList<>();
        String user = UUID.randomUUID().toString();
        String user2 = UUID.randomUUID().toString();

        Like like = Like.builder()
                .post(getPostMock())
                .userId(user)
                .build();
        Like like2 = Like.builder()
                .post(getPostMock())
                .userId(user2)
                .build();


        likesStringList.add(user);
        likesStringList.add(user2);

        likesList.add(like);
        likesList.add(like2);

        when(likeRepository.findAllByPostId(any(String.class))).thenReturn(likesList);

        List<String> response = likeService.getAllLikesFromPost("id");

        verify(likeRepository,times(1)).findAllByPostId(any(String.class));

        assertNotNull(response);

        assertEquals(likesStringList,response);

    }

    @Test
    void addLike() {
        Post post = getPostMock();
        post.addLike();

        Like like = Like.builder()
                .userId(UUID.randomUUID().toString())
                .post(post)
                .build();

        Mockito.doNothing().when(postProducer).SendNotificationLike(any(String.class),any(String.class),any(String.class),any(String.class),any(String.class));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(likeRepository.save(any(Like.class))).thenReturn(like);
        when(likeRepository.existsByPostId(any(String.class))).thenReturn(true);
        when(postRepository.getReferenceById(any(String.class))).thenReturn(post);

        likeService.addLike("id","id");

        verify(postRepository,times(1)).getReferenceById(any(String.class));
        verify(likeRepository,times(1)).existsByPostId(any(String.class));
        verify(postProducer,times(1)).SendNotificationLike(any(String.class),any(String.class),any(String.class),any(String.class),any(String.class));

    }

    @Test
    void deleteLike() {
        Post post = getPostMock();
        post.addLike();
        post.addLike();

        Like like = Like.builder()
                .userId(UUID.randomUUID().toString())
                .post(post)
                .build();
        when(postRepository.getReferenceById(any(String.class))).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(likeRepository.getReferenceByPostIdAndUserId(any(String.class),any(String.class))).thenReturn(like);
        Mockito.doNothing().when(likeRepository).delete(any(Like.class));

        likeService.deleteLike("testID","testID");

        verify(postRepository,times(1)).getReferenceById(any(String.class));
        verify(postRepository,times(1)).save(any(Post.class));
        verify(likeRepository,times(1)).getReferenceByPostIdAndUserId(any(String.class),any(String.class));
        assertEquals(1,post.getLikes());

    }

    private static Post getPostMock() {
        Post post = Post.builder()
                .songId(UUID.randomUUID().toString())
                .userId("testUserID")
                .description("Descrition")
                .likes(0L)
                .likesList(new ArrayList<>())
                .build();
        return post;
    }
}