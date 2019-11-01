package com.example.clutter.ServerProxy;

import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.example.clutter.sdk.CluttterClient;
import com.example.clutter.sdk.model.FollowerList;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.User;

//class Task extends AsyncTask<Void, Void, Void> {
//    private CluttterClient client;
//    private ApiClientFactory factory;
//
//    @Override
//    protected Void doInBackground(Void... voids) {
//        this.factory = new ApiClientFactory();
//        this.client = factory.build(CluttterClient.class);
//
//            StatusList statuses = client.userFeedGet();
//
//            return null;
//    }
//}

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

    public Message postStatus() {
        return client.userSendstatusPost();
    }

    public User getUser() {
        return client.userUserhandleGet("@roscoeevans");
    }
}