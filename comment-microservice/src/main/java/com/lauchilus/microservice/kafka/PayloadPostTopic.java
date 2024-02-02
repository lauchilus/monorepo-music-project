package com.lauchilus.microservice.kafka;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PayloadPostTopic {

    private String postId;
    private String userId;
}
