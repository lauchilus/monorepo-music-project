package com.lauchilus.microservice.comments;

import com.lauchilus.microservice.post.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {

    List<Comment> findAllByPost(Post post);
}
