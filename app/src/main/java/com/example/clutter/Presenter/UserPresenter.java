package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.UserMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.Model.UserInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.UserActivity;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.StatusListStatusesItem;
import com.example.clutter.sdk.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserPresenter implements UserMVP.Presenter {
    private UserActivity view;

    private class UnfollowUserAsync extends AsyncTask<Void, Void, Message> {

        private UnfollowUserAsync() {
            //Blank constructor
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            return proxy.unfollowUser();
        }

        @Override
        protected void onPostExecute(Message message) {
            view.unfollowUser(message);
        }
    }

    private class FollowUserAsync extends AsyncTask<Void, Void, Message> {

        private FollowUserAsync() {
            //Blank constructor
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            return proxy.followUser();
        }

        @Override
        protected void onPostExecute(Message message) {
            view.followUser(message);
        }
    }

    private class GetUserInfoAsync extends AsyncTask<Void, Void, UserInfo> {

        private GetUserInfoAsync() {
            //Blank constructor
        }

        @Override
        protected UserInfo doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            User user = proxy.getUser();


            String profilePic = user.getProfilePic();
            String userHandle = user.getUserHandle();
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

        private GetUserStoryAsync() {
            //Blank constructor
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            StatusList listOfStatuses = proxy.getUserStory();

            List<StatusListStatusesItem> statusItems = listOfStatuses.getStatuses();
            List<com.example.clutter.Model.Status> statusesToPost = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < statusItems.size() ; i++) {
                StatusListStatusesItem currentStatus = statusItems.get(i);

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
            view.displayStatuses(statuses);
        }
    }

    public UserPresenter(UserActivity view) {
        this.view = view;
    }

    @Override
    public void createDummyData() {
        new GetUserStoryAsync().execute();
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
}
