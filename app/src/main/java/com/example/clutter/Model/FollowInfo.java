package com.example.clutter.Model;

public class FollowInfo {
    public String profilePic;
    public String name;

    public FollowInfo(String profilePic, String name) {
        this.profilePic = profilePic;
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
