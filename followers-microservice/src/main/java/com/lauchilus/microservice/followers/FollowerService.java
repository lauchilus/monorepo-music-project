package com.lauchilus.microservice.followers;

import com.lauchilus.microservice.followers.kafka.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository repository;
    private final Producer producer;

    public void follow(String follower, String followee) {
        repository.insert(Follower.builder()
                .follower(follower)
                .followee(followee)
                .build());

        producer.SendNotificationLike(followee,"New Follower","You have a new follower", follower);
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
