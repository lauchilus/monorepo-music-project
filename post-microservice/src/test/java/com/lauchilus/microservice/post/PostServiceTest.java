package com.lauchilus.microservice.post;

import com.lauchilus.microservice.kafka.producer.PayloadPostTopic;
import com.lauchilus.microservice.kafka.producer.PostProducer;
import com.lauchilus.microservice.post.dto.CreateDto;
import com.lauchilus.microservice.post.dto.GetPostDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostProducer postProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository,postProducer,null);
    }

    @Test
    public void createPost__shouldReturnPost() {
        CreateDto create = new CreateDto("Descrition","25ypHCQpDX2nrDFly7eZLZ");
        String uid = "testUid";

        Post post = getPostMock();

        PayloadPostTopic payload = PayloadPostTopic.builder()
                .postId(post.getId())
                .userId(post.getUserId())
                .build();
        when(postRepository.save(any(Post.class))).thenReturn(post);
        Mockito.doNothing().when(postProducer).sendMessage(payload);

        Post response = postService.createPost(create,uid);

        verify(postRepository,times(1)).save(any(Post.class));
        verify(postProducer,times(1)).sendMessage(any(PayloadPostTopic.class));

        assertNotNull(response);
        assertEquals(response,post);
    }


    @Test
    void getPost__shouldReturnPost() {
        Post post = getPostMock();
        when(postRepository.getReferenceById(any(String.class))).thenReturn(post);
        GetPostDto response = postService.getPost("id");

        verify(postRepository,times(1)).getReferenceById(any(String.class));

        assertNotNull(response);
        assertEquals(post,response);
    }

    @Test
    void getAllPostFromUser__shouldReturnListOfPosts() {
        List<Post> listPosts = new ArrayList<>();
        Post post = getPostMock();
        Post post2 = getPostMock();

        listPosts.add(post);
        listPosts.add(post2);

        when(postRepository.findAllByUserId(any(String.class))).thenReturn(listPosts);

        List<GetPostDto> response = postService.getAllPostFromUser("idtest");

        verify(postRepository,times(1)).findAllByUserId(any(String.class));
        assertNotNull(response);

        assertEquals(listPosts,response);

    }

    @Test
    void deletePost() {
        Mockito.doNothing().when(postProducer).sendMessageDeleteComments(any(String.class));
        Mockito.doNothing().when(postRepository).deleteById(any(String.class));

        postService.deletePost("test");

        verify(postRepository,times(1)).deleteById(any(String.class));
        verify(postProducer,times(1)).sendMessageDeleteComments(any(String.class));

    }

    private static Post getPostMock() {
        Post post = Post.builder()
                .songId("25ypHCQpDX2nrDFly7eZLZ")
                .userId("testUserID")
                .description("Descrition")
                .likes(0L)
                .likesList(new ArrayList<>())
                .build();
        return post;
    }
}