package com.lauchilus.microservice.comments.dto;

public record ResponseComments(
        String commentId,
        String postId,
        String text,
        String userId
) {
}
