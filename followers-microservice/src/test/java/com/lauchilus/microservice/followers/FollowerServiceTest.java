package com.lauchilus.microservice.followers;

import com.lauchilus.microservice.followers.kafka.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
class FollowerServiceTest {

    @InjectMocks
    private FollowerService followerService;

    @Mock
    private FollowerRepository repository;

    @Mock
    private Producer producer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        followerService = new FollowerService(repository,producer);
    }

    @Test
    void followTest() {


        String follower = "followerId";
        String followee = "followeeId";

        when(repository.insert(any(Follower.class))).thenReturn(new Follower());

        Mockito.doNothing().when(producer).SendNotificationLike(eq(followee), Mockito.anyString(), Mockito.anyString(), eq(follower), Mockito.anyString());

        followerService.follow(follower, followee);

        verify(repository, times(1)).insert(any(Follower.class));

        verify(producer, times(1)).SendNotificationLike(followee, "New Follower", "You have a new follower", follower, "follow");
    }


    @Test
    void unfollowTest() {
        String unfollower = "unfollowerId";
        String unfollowee = "unfolloweeId";

        doNothing().when(repository).deleteByFollowerAndFollowee(unfollower, unfollowee);

        followerService.unfollow(unfollower, unfollowee);

        verify(repository, times(1)).deleteByFollowerAndFollowee(unfollower, unfollowee);

    }

    @Test
    void userFollows() {
        String follower = "followerId";

        List<Follower> mockFollowers = new ArrayList<>();
        mockFollowers.add(Follower.builder().follower("follower1").followee("folowee1").build());
        mockFollowers.add(Follower.builder().follower("follower2").followee("folowee2").build());

        when(repository.findAllByFollower(eq(follower), 0, 0)).thenReturn(mockFollowers);

        List<Follower> result = followerService.userFollows(follower,0,0);

         verify(repository, times(1)).findAllByFollower(eq(follower),0,0);

        assertEquals(2, result.size());
    }

    @Test
    void userFolloweeTest() {

        String followee = "followeeId";


        List<Follower> mockFollowers = new ArrayList<>();
        mockFollowers.add(Follower.builder().follower("follower1").followee(followee).build());
        mockFollowers.add(Follower.builder().follower("follower2").followee(followee).build());

        when(repository.findAllByFollowee(eq(followee),0,0)).thenReturn(mockFollowers);

        List<Follower> result = followerService.userFollowee(followee,0,0);

        verify(repository, times(1)).findAllByFollowee(eq(followee),0,0);

        assertEquals(2, result.size());
    }

    @Test
    void followsCountTest() {
        String follower = "followerId";

        long mockFollowsCount = 10;

        when(repository.countByFollower(eq(follower))).thenReturn(mockFollowsCount);

        Long result = followerService.followsCount(follower);

        verify(repository, times(1)).countByFollower(eq(follower));

        assertEquals(mockFollowsCount, result);
    }

    @Test
    void followeesCountTest() {
        String followee = "followeeId";

        long mockFolloweesCount = 5;

        when(repository.countByFollowee(eq(followee))).thenReturn(mockFolloweesCount);

        Long result = followerService.followeesCount(followee);

        verify(repository, times(1)).countByFollowee(eq(followee));

        assertEquals(mockFolloweesCount, result);
    }
}