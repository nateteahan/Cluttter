package com.example.clutter.ServerProxy;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.clutter.sdk.CluttterClient;
import com.example.clutter.sdk.model.Authorization;
import com.example.clutter.sdk.model.Empty;
import com.example.clutter.sdk.model.FollowerList;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.Hashtag;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.PostPicture;
import com.example.clutter.sdk.model.RegisterUser;
import com.example.clutter.sdk.model.SendStatusRequest;
import com.example.clutter.sdk.model.SignInUser;
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

    public Message registerUser(RegisterUser user) {
        return client.userUserhandlePost(user.getUserhandle(), user);
    }

    public Message updateUserProfile(PostPicture profilePic) {
        return client.userUserhandleProfilepicPost(profilePic.getUserhandle(), profilePic);
    }

    public Authorization signIn(SignInUser user) {
        return client.userUserhandleSigninPost(user.getUserhandle(), user);
    }

    public StatusList getFeed(String userhandle) {
        StatusList statusList = client.userUserhandleFeedGet(userhandle);

        return statusList;
    }

    public StatusList getUserStory(String handle) {
        return client.userUserhandleStoryGet(handle);
    }

    public FollowingList getFollowing(String userhandle) {
        return client.userUserhandleFollowingGet(userhandle);
    }

    public FollowerList getFollowers(String userhandle) {
        return client.userUserhandleFollowersGet(userhandle);
    }

    public StatusList getHashtagStatuses(String hashtag) {
        return client.hashtagGet(hashtag);
    }

    public Message postStatus(String userHandle, SendStatusRequest status) {
        return client.userUserhandlePoststatusPost(userHandle, status);
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

    public Empty postHashtag(String hashtag, Hashtag request) {
        return client.hashtagPost(hashtag, request);
    }
}