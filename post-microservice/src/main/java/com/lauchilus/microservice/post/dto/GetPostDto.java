package com.lauchilus.microservice.post.dto;

import com.lauchilus.microservice.post.SpotifyTrackInfo;

public record GetPostDto(

        String id,
        String userId,
        String description,
        Long likes,

        SpotifyTrackInfo track
) {
}
