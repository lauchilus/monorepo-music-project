package com.lauchilus.microservice.followers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/followers")
@RequiredArgsConstructor
public class FollowerController {
    private final FollowerService service;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestParam String follower, @RequestParam String followee){
        service.follow(follower,followee);
        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestParam String follower, @RequestParam String followee){
        service.follow(follower,followee);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @GetMapping("/follows/{follower}")
    public ResponseEntity<List<Follower>> userFollows(@PathVariable String follower,@RequestParam(required = false, defaultValue = "0")Integer limit,@RequestParam(required = false, defaultValue = "0")Integer offset){
        return new ResponseEntity<List<Follower>>(service.userFollows(follower,limit,offset),HttpStatus.OK);
    }

    @GetMapping("/followees/{followee}")
    public ResponseEntity<List<Follower>> userFollowes(@PathVariable String followee,@RequestParam(required = false, defaultValue = "0")Integer limit,@RequestParam(required = false, defaultValue = "0")Integer offset){
        return new ResponseEntity<List<Follower>>(service.userFollowee(followee,limit,offset),HttpStatus.OK);
    }

    @GetMapping("/follows/count")
    public ResponseEntity<Long> countFollows(@RequestParam String follower){
        return new ResponseEntity<Long>(service.followsCount(follower),HttpStatus.OK);
    }

    @GetMapping("/followees/count")
    public ResponseEntity<Long> countFollowees(@RequestParam String followee){
        return new ResponseEntity<Long>(service.followeesCount(followee),HttpStatus.OK);
    }
}
