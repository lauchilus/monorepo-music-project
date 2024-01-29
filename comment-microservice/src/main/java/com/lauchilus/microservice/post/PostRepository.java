package com.lauchilus.microservice.post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    Post findByPostId(String postId);

    boolean existsByPostId(String id);
}
