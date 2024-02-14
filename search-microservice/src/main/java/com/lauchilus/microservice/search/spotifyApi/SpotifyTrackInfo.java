package com.lauchilus.microservice.search.spotifyApi;

public record SpotifyTrackInfo(
        String name,
        String id,
        String externalUrl,
        String previewUrl,
        String imageUrl,
        String artist
) {
}
