package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.UserMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.Status;
import com.example.clutter.Model.UserInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.UserActivity;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.NewStatusList;
import com.example.clutter.sdk.model.NewStatusListStatusesItem;
import com.example.clutter.sdk.model.User;

import java.util.List;

public class UserPresenter implements UserMVP.Presenter {
    protected String followeeHandle;
    protected String followerHandle;
    private UserActivity view;
    private String userhandle;
    private String lastKey;
    private List<Status> statusesToPost;

    private class UnfollowUserAsync extends AsyncTask<Void, Void, Message> {

        private UnfollowUserAsync() {
            //Blank constructor
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            return proxy.unfollowUser(followerHandle, followeeHandle);
        }

        @Override
        protected void onPostExecute(Message message) {
            view.unfollowUser(message.getMessage());
        }
    }

    private class FollowUserAsync extends AsyncTask<Void, Void, Message> {

        private FollowUserAsync() {
            //Blank constructor
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            return proxy.followUser(followerHandle, followeeHandle);
        }

        @Override
        protected void onPostExecute(Message message) {

            view.followUser(message.getMessage());
        }
    }

    private class GetUserInfoAsync extends AsyncTask<Void, Void, UserInfo> {

        private GetUserInfoAsync() {
//            Blank constructor
        }

        @Override
        protected UserInfo doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            User user = proxy.getUser(followeeHandle);

            String profilePic = user.getProfilePic();
            String userHandle = "@" + user.getUserHandle();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();

            UserInfo userInfo = new UserInfo(profilePic, userHandle, firstName, lastName);

            return userInfo;
        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {
                view.assignUserFields(userInfo);
        }
    }

    private class GetUserStoryAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetUserStoryAsync(String handle, String key) {
            userhandle = handle;
            lastKey = key;
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            if (lastKey == null) {
                lastKey = "";
            }

            NewStatusList listOfStatuses = proxy.getUserStory(followeeHandle, lastKey);
            lastKey = listOfStatuses.getLastKey();

            List<NewStatusListStatusesItem> statusItems = listOfStatuses.getStatuses();
//            List<com.example.clutter.Model.Status> statusesToPost = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < statusItems.size() ; i++) {
                NewStatusListStatusesItem currentStatus = statusItems.get(i);

                String profilePic = currentStatus.getProfilePic();
                String firstName = currentStatus.getFirstName();
                String userHandle = currentStatus.getUserHandle();
                String time = currentStatus.getTime();
                String status = currentStatus.getStatus();
                String imageAttachment = currentStatus.getImageAttachment();
                String videoAttachment = currentStatus.getVideoAttachment();

                com.example.clutter.Model.Status clientStatus = new com.example.clutter.Model.Status(profilePic, firstName, userHandle,
                        time, status, imageAttachment, videoAttachment);
                statusesToPost.add(clientStatus);
            }

//            statuses = statusesToPost;
            return statusesToPost;
        }

        @Override
        protected void onPostExecute(List<com.example.clutter.Model.Status> statuses) {
            view.displayStatuses(statuses, lastKey);
        }
    }

    private class GetFollowState extends AsyncTask<Void, Void, Message> {
        private String follower;
        private String followee;

        public GetFollowState(String follower, String followee) {
            this.follower = follower;
            this.followee = followee;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            return proxy.isFollowing(follower, followee);
        }

        @Override
        protected void onPostExecute(Message message) {
            view.setButtonState(message.getMessage());
        }
    }

    public UserPresenter(UserActivity view, String userHandle) {
        // Bug fix for the URL encoding of the "@" symbol
        this.followerHandle = ModelSingleton.getmUser().getUserHandle().replace("@", "");
        this.followeeHandle = userHandle.replace("@", "");
        this.view = view;
    }

    @Override
    public void createDummyData(List<Status> statuses, String handle, String key) {
        statusesToPost = statuses;
        userhandle = handle;

        if (key != null) {
            key = parseLastKey(key);
        }

        new GetUserStoryAsync(handle, key).execute();
    }

    public void getUserInfo() {
        new GetUserInfoAsync().execute();
    }

    public void followUser() {
        new FollowUserAsync().execute();
    }

    public void unfollowUser() {
        new UnfollowUserAsync().execute();
    }

    public void isFollowing(String followerHandle, String followeeHandle) {
        new GetFollowState(followerHandle, followeeHandle).execute();
    }

    private String parseLastKey(String key) {
        //Remove special characters... Invalid in api gateway
        key = key.replaceAll(" ", "");
        key = key.replaceAll("/", "");
        key = key.replaceAll(":", "");

        return key;
    }
}
