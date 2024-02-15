package com.lauchilus.microservice.search.spotifyApi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SpotifyApiResponseParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<SpotifyTrackInfo> parseTracks(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SpotifyTrackInfo> trackInfoList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("tracks").path("items");

            for (JsonNode trackNode : itemsNode) {
                String name = trackNode.path("name").asText();
                String id = trackNode.path("id").asText();
                String externalUrl = trackNode.path("external_urls").path("spotify").asText();
                String previewUrl = trackNode.path("preview_url").asText();
                //String artist = trackNode.path("artist").asText();

                JsonNode jsonNodeArtist = trackNode.path("artists");
                String artist = jsonNodeArtist.isArray() && !jsonNodeArtist.isEmpty() ? jsonNodeArtist.get(0).path("name").asText() : "";

                // Obtener la URL de la imagen del artista desde el nodo del álbum
                String imageUrl = getArtistImageUrl(trackNode);

                SpotifyTrackInfo trackInfo = new SpotifyTrackInfo(name, id, externalUrl, previewUrl, imageUrl,artist);
                trackInfoList.add(trackInfo);
            }

            return trackInfoList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private String getArtistImageUrl(JsonNode trackNode) {
        JsonNode albumNode = trackNode.path("album");
        JsonNode imagesNode = albumNode.path("images");
        if (imagesNode.isArray() && !imagesNode.isEmpty()) {
            return imagesNode.get(0).path("url").asText();
        }
        return "";
    }


    private static String getNode(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.path(fieldName);
        return fieldNode.isMissingNode() ? null : fieldNode.asText();
    }



    private static String getImageUrl(JsonNode node, int width, int height) {
        JsonNode imagesNode = node.path("images");
        if (imagesNode.isArray()) {
            for (JsonNode imageNode : imagesNode) {
                int imageWidth = imageNode.path("width").asInt();
                int imageHeight = imageNode.path("height").asInt();
                if (imageWidth == width && imageHeight == height) {
                    return imageNode.path("url").asText();
                }
            }
        }
        return null;
    }

    public SpotifyTrackInfo parseTrackById(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode trackNode = objectMapper.readTree(body);

            String name = trackNode.path("name").asText();
            String id = trackNode.path("id").asText();
            String externalUrl = trackNode.path("external_urls").path("spotify").asText();
            String previewUrl = trackNode.path("preview_url").asText();

            // En este caso, asumimos que la imagen es la primera en la lista de imágenes del álbum
            JsonNode albumNode = trackNode.path("album");
            String imageUrl = albumNode.path("images").get(0).path("url").asText();
            String albumName = albumNode.path("name").asText();

            JsonNode artistsNode = trackNode.path("artists");
            String artistName = "";
            if (artistsNode.isArray() && artistsNode.size() > 0) {
                JsonNode firstArtistNode = artistsNode.get(0);
                artistName = firstArtistNode.path("name").asText();
            }


            SpotifyTrackInfo trackDetails = new SpotifyTrackInfo(name,id,externalUrl,previewUrl,imageUrl,artistName);

            return trackDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


