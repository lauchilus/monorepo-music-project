package com.lauchilus.microservice.comments.dto;

public record AddCommentDto(
        String userId,
        String postId,
        String text
) {
}
