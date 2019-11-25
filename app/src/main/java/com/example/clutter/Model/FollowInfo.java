package com.example.clutter.Model;

public class FollowInfo {
    public String profilePic;
    public String handle;

    public FollowInfo(String profilePic, String name) {
        this.profilePic = profilePic;
        this.handle = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return handle;
    }

    public void setName(String name) {
        this.handle = name;
    }
}

