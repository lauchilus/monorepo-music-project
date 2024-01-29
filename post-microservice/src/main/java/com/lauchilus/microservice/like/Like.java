package com.lauchilus.microservice.like;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lauchilus.microservice.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "likes")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Like {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    private String userId;
}
