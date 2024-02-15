package com.lauchilus.microservice.post;

public record SpotifyTrackInfo(
        String name,
        String id,
        String externalUrl,
        String previewUrl,
        String imageUrl,
        String artist
) {
}
