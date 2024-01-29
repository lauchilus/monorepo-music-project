package com.lauchilus.microservice.post.dto;

public record CreateDto(
        String userId,
        String description,
        String songId
) {
}
