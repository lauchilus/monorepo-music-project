package com.lauchilus.microservice.comments;

import com.lauchilus.microservice.post.Post;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Comment {

    @Id
    private String id;
    @DBRef(lazy = false)
    private Post post;
    private String userId;
    private String text;


}
