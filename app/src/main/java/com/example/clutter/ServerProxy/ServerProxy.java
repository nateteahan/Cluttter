package com.example.clutter.ServerProxy;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.clutter.sdk.CluttterClient;
import com.example.clutter.sdk.model.FollowerList;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.User;

public class ServerProxy {
    private ApiClientFactory factory;
    private CluttterClient client;

    //
    public ServerProxy() {
        this.factory = new ApiClientFactory();
        this.client = factory.build(CluttterClient.class);
    }

    public StatusList getFeed() {
        StatusList statusList = client.userFeedGet();

        return statusList;
    }

    public StatusList getUserStory() {
        return client.userUserhandleStoryGet("@nateteahan");
    }

    public FollowingList getFollowing() {
        return client.userUserhandleFollowingGet("@nateteahan");
    }

    public FollowerList getFollowers() {
        return client.userUserhandleFollowersGet("@nateteahan");
    }

    public StatusList getHashtagStatuses() {
        return client.hashtagGet("#CS");
    }

    public Message postStatus(String userHandle) {
        return client.userUserhandlePoststatusPost(userHandle);
    }

    public User getUser(String userHandle) {
        return client.userUserhandleGet(userHandle);
    }

    public Message followUser(String followerHandle, String followeeHandle) {
        return client.userUserhandleFollowSecondaryUserPost(followerHandle, followeeHandle);
    }

    public Message unfollowUser(String followerHandle, String followeeHandle) {
        return client.userUserhandleFollowSecondaryUserDelete(followerHandle, followeeHandle);
    }
}