package com.lauchilus.microservice.followers.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private String recipientToken;
    private String title;
    private String body;
    private String username;
    private Map<String, String> data;

}
