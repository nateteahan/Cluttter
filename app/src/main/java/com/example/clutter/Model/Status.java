package com.example.clutter.Model;

import android.widget.ImageView;
import android.widget.VideoView;

public class Status {
//    private ImageView profilePic;
    private String userFirstName;
    private String userHandle;
    private String time;
    private String status;
    private VideoView videoAttachemnt;
    private ImageView imageAttachment;

    //Constructor
    public Status(String firstName, String handle, String time, String status, ImageView imageView, VideoView videoView) {
//        this.profilePic = icon;
        this.userFirstName = firstName;
        this.userHandle = handle;
        this.time = time;
        this.status = status;
        this.imageAttachment = imageView;
        this.videoAttachemnt = videoView;
    }

//    public ImageView getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(ImageView profilePic) {
//        this.profilePic = profilePic;
//    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VideoView getVideoAttachemnt() {
        return videoAttachemnt;
    }

    public void setVideoAttachemnt(VideoView videoAttachemnt) {
        this.videoAttachemnt = videoAttachemnt;
    }

    public ImageView getImageAttachment() {
        return imageAttachment;
    }

    public void setImageAttachment(ImageView imageAttachment) {
        this.imageAttachment = imageAttachment;
    }
}
