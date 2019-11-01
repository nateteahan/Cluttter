package com.example.clutter.Model;

public class UserInfo {
    public String profilePic;
    public String userHandle;
    public String firstName;
    public String lastName;

    public UserInfo(String profilePic, String userHandle, String firstName, String lastName) {
        this.profilePic = profilePic;
        this.userHandle = userHandle;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
