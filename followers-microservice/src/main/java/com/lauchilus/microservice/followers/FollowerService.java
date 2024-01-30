package com.lauchilus.microservice.followers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository repository;

    public void follow(String follower, String followee) {
        repository.insert(Follower.builder()
                .follower(follower)
                .followee(followee)
                .build());
    }

    public void unfollow(String unfollower, String unfollowee) {
        repository.deleteByFollowerAndFollowee(unfollower, unfollowee);
    }

    public List<Follower> userFollows(String follower) {
        return repository.findAllByFollower(follower);
    }

    public List<Follower> userFollowee(String followee) {
        return repository.findAllByFollowee(followee);
    }

    public Long followsCount(String follower) {
        return repository.countByFollower(follower);
    }

    public Long followeesCount(String followee) {
        return repository.countByFollowee(followee);
    }

}
