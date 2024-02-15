package com.lauchilus.microservice.post;

import com.lauchilus.microservice.comments.Comment;
import jakarta.ws.rs.ext.ParamConverter;
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
@Data
public class Post {

    @Id
    private String id;
    private String postId;
    private String userId;
    @DBRef()
    private List<Comment> comments;
}
