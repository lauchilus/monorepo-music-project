package com.lauchilus.microservice.search.spotifyApi;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyApiResponseParser responseParser;
    private final SpotifyApi spotifyApi;
    private static final String searchUrl = "https://api.spotify.com/v1/search?q=luis+miguel&type=track%2Cartist";

    private final RestTemplate restTemplate;
    private String type = ModelObjectType.ARTIST.getType();

    public List<SpotifyTrackInfo> getTrack(String query) throws IOException, ParseException, SpotifyWebApiException {
        String q = String.format("https://api.spotify.com/v1/search?q=%s&type=track,artist",query);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + spotifyApi.getAccessToken());

        //SearchItemRequest sir = spotifyApi.searchItem(query, type).build();
        //final SearchResult searchResult = sir.execute();
        //SpotifyApiResponseParser.parseTracks(searchResult.getTracks().toString());
        ResponseEntity<String> response = restTemplate.exchange(
                q,
                HttpMethod.GET,
                new HttpEntity<>(headers),  // Añadir los headers a la solicitud
                String.class);
        List<SpotifyTrackInfo> res = responseParser.parseTracks(response.getBody());
        System.out.println(response.getBody());
        return res;
    }
}
