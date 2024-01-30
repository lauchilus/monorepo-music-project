package com.lauchilus.microservice.followers;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Follower {

    @Id
    private String id;
    private String follower;
    private String followee;

}
