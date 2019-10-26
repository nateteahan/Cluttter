package com.example.clutter.Model;

import java.util.List;

public class User {
    private String userHandle;
    private String userPassword;
    private List<Status> userFeed;
    private List<Status> userStory;
    private String userStatus;

    public User() {
    }

    public User(String userHandle, String userPassword) {
        this.userHandle = userHandle;
        this.userPassword = userPassword;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<Status> getUserFeed() {
        return userFeed;
    }

    public void setUserFeed(List<Status> userFeed) {
        this.userFeed = userFeed;
    }

    public List<Status> getUserStory() {
        return userStory;
    }

    public void setUserStory(List<Status> userStory) {
        this.userStory = userStory;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
