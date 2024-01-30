package com.lauchilus.microservice.followers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends MongoRepository<Follower,String> {


    List<Follower> findAllByFollower(String follower);

    Long countByFollower(String follower);

    List<Follower> findAllByFollowee(String followee);

    Long countByFollowee(String followee);

    void deleteByFollowerAndFollowee(String unfollower, String unfollowee);
}
