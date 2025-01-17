package com.example.clutter.Model;

public class User {
    private String userHandle;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePic;
    private String authToken;

    public User(String userHandle, String firstName, String lastName, String email) {
        this.userHandle = userHandle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String userHandle) {
        this.userHandle = userHandle;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
