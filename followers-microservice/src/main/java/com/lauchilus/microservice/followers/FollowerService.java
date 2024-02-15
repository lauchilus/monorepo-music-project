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
        Follower f =repository.insert(Follower.builder()
                .follower(follower)
                .followee(followee)
                .build());

        producer.SendNotificationLike(followee,"New Follower","You have a new follower", follower,"follow");
    }

    public void unfollow(String unfollower, String unfollowee) {
        repository.deleteByFollowerAndFollowee(unfollower, unfollowee);
    }

    public List<Follower> userFollows(String follower,Integer limit, Integer offset) {
        return repository.findAllByFollower(follower,limit,offset);
    }

    public List<Follower> userFollowee(String followee,Integer limit,Integer offset) {
        return repository.findAllByFollowee(followee,limit,offset);
    }

    public Long followsCount(String follower) {
        return repository.countByFollower(follower);
    }

    public Long followeesCount(String followee) {
        return repository.countByFollowee(followee);
    }

}
