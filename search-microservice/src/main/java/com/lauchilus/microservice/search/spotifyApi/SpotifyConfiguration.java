package com.lauchilus.microservice.search.spotifyApi;


import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;

@Configuration
public class SpotifyConfiguration {

    @Value("${spotify.redirectUrl}")
    private String customIp;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.client.id}")
    private String clientId;


    @Bean
    public SpotifyApi getSpotifyObject() throws IOException, ParseException, SpotifyWebApiException {
        URI redirectUrl = SpotifyHttpManager.makeUri(customIp);
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUrl)
                .build();
        ClientCredentialsRequest ccr = spotifyApi.clientCredentials().build();
        final ClientCredentials clientCredentials = ccr.execute();
        spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        return spotifyApi;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
