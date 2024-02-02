package com.lauchilus.microservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PostTopicConfig {

    @Bean
    public NewTopic postTopic(){
        return TopicBuilder
                .name("post-creation")
                .build();
    }

}
