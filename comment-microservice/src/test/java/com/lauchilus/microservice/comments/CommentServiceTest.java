package com.lauchilus.microservice.comments;

import com.lauchilus.microservice.comments.dto.AddCommentDto;
import com.lauchilus.microservice.comments.dto.ResponseComments;
import com.lauchilus.microservice.kafka.Producer;
import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Producer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository,postRepository,producer);
    }

    @Test
    public void testAddComment() {

        AddCommentDto commentDto = new AddCommentDto("userId", "postId", "text");


        Post mockPost = Post.builder()
                .id("postId")
                .postId(commentDto.postId())
                .comments(new ArrayList<>())
                .userId(commentDto.userId())
                .build();
        Mockito.when(postRepository.findByPostId(commentDto.postId())).thenReturn(mockPost);
        Mockito.when(postRepository.existsByPostId(mockPost.getId())).thenReturn(false);


        Comment mockComment = Comment.builder()
                .id("commentId")
                .text(commentDto.text())
                .post(mockPost)
                .userId(commentDto.userId())
                .build();
        Mockito.when(commentRepository.insert(Mockito.any(Comment.class))).thenReturn(mockComment);


        Mockito.doNothing().when(producer).SendNotificationComment(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());


        ResponseComments result = commentService.addComment(commentDto);

        assertEquals(mockComment.getId(), result.commentId());
        assertEquals(mockComment.getPost().getPostId(), result.postId());
        assertEquals(mockComment.getText(), result.text());
        assertEquals(mockComment.getUserId(), result.userId());
    }

    @Test
    public void testAddCommentWithExistingPostId() {

        AddCommentDto commentDto = new AddCommentDto("postId", "userId", "text");


        Post mockPost = new Post();
        mockPost.setId("postId");
        Mockito.when(postRepository.findByPostId(commentDto.postId())).thenReturn(mockPost);
        Mockito.when(postRepository.existsByPostId(mockPost.getId())).thenReturn(true);


        assertThrows(ResponseStatusException.class, () -> commentService.addComment(commentDto));
    }

    @Test
    void getAllPostComments() {
        String postId = "postId";
        Post mockPost = Post.builder()
                .id("postId")
                .postId("postId")
                .comments(new ArrayList<>())
                .userId("userId")
                .build();

        Comment comment1 = Comment.builder().id("commentId1").post(mockPost).text("text1").userId("userId1").build();
        Comment comment2 = Comment.builder().id("commentId2").post(mockPost).text("text2").userId("userId2").build();

        List<Comment> mockComments = Arrays.asList(comment1, comment2);


        Mockito.when(postRepository.findByPostId(postId)).thenReturn(mockPost);


        Mockito.when(commentRepository.findAllByPost(mockPost)).thenReturn(mockComments);


        List<ResponseComments> result = commentService.getAllPostComments(postId);


        assertEquals(2, result.size());

        ResponseComments resultComment1 = result.get(0);
        assertEquals("commentId1", resultComment1.commentId());
        assertEquals("postId", resultComment1.postId());
        assertEquals("text1", resultComment1.text());
        assertEquals("userId1", resultComment1.userId());

        ResponseComments resultComment2 = result.get(1);
        assertEquals("commentId2", resultComment2.commentId());
        assertEquals("postId", resultComment2.postId());
        assertEquals("text2", resultComment2.text());
        assertEquals("userId2", resultComment2.userId());
    }


    @Test
    public void testDeleteComment() {

        String commentId = "commentId";

        Mockito.doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId);

        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(commentId);
    }

    @Test
    public void testDeleteCommentWithNonexistentCommentId() {

        String nonExistentCommentId = "nonExistentCommentId";

        Mockito.doThrow(new EmptyResultDataAccessException(1)).when(commentRepository).deleteById(nonExistentCommentId);

        assertThrows(EmptyResultDataAccessException.class, () -> commentService.deleteComment(nonExistentCommentId));
    }

    @Test
    void deleteAllCommentsForPost() {

        Post mockPost = new Post();
        mockPost.setId("postId");

        Mockito.doNothing().when(commentRepository).deleteAllByPost(mockPost);

        commentService.deleteAllCommentsForPost(mockPost);

        Mockito.verify(commentRepository, Mockito.times(1)).deleteAllByPost(mockPost);

    }
}