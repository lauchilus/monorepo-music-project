package com.lauchilus.microservice.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lauchilus.microservice.like.Like;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "posts")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Post {

    @Id
    @UuidGenerator
    private String id;
    private String songId;
    private String userId;
    @ColumnDefault("")
    private String description;
    @ColumnDefault("0")
    private Long likes;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.EAGER)
    private List<Like> likesList = new ArrayList<>();

    public void addLike() {
        this.likes++;
    }

    public void deleteLike() {
        this.likes--;
    }

}

