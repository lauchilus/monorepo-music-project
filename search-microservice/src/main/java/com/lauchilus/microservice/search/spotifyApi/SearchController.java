package com.lauchilus.microservice.search.spotifyApi;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SpotifyService spotifyService;

    @GetMapping
    public ResponseEntity<List<SpotifyTrackInfo>> getTracks(@RequestParam String q,@RequestParam(required = false,defaultValue = "5") String limit,@RequestParam(required = false,defaultValue = "0") String offset) throws IOException, ParseException, SpotifyWebApiException {
        return new ResponseEntity<>(spotifyService.getTrack(q,limit,offset), HttpStatus.OK);
    }
}
