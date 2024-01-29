package com.lauchilus.microservice.comments;

import com.lauchilus.microservice.comments.dto.AddCommentDto;
import com.lauchilus.microservice.comments.dto.ResponseComments;
import com.lauchilus.microservice.post.Post;
import com.lauchilus.microservice.post.PostRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment addComment(AddCommentDto commentDto){
        Post post = postRepository.findByPostId(commentDto.postId());
        if(postRepository.existsByPostId(post.getId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Comment comment = Comment.builder()
                .userId(commentDto.userId())
                .post(post)
                .text(commentDto.text())
                .build();
        commentRepository.insert(comment);
        return comment;
    }

    public List<ResponseComments> getAllPostComments(String postId){
        Post post = postRepository.findByPostId(postId);
        List<ResponseComments> comments = commentRepository.findAllByPost(post).stream()
                .map(comment -> new ResponseComments(comment.getId(), comment.getPost().getPostId(), comment.getText(), comment.getUserId()))
                .collect(Collectors.toList());
        return comments;
    }

    public void deleteComment(String commentId){
        commentRepository.deleteById(commentId);
    }
}
