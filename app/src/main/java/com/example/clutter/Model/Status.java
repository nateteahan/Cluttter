package com.example.clutter.Model;

public class Status {
//    private ImageView profilePic;
    private String firstName;
    private String userHandle;
    private String time;
    private String status;
    private String imageAttachment;
    private String videoAttachment;

    //Constructor
    public Status(String firstName, String handle, String time, String status, String imageURL, String videoURL) {
//        this.profilePic = icon;
        this.firstName = firstName;
        this.userHandle = handle;
        this.time = time;
        this.status = status;
        this.imageAttachment = imageURL;
        this.videoAttachment = videoURL;
    }

//    public ImageView getProfilePic() {
//        return profilePic;
//    }
//
//    public void setProfilePic(ImageView profilePic) {
//        this.profilePic = profilePic;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getVideoAttachment() {
        return videoAttachment;
    }

    public void setVideoAttachment(String videoAttachment) {
        this.videoAttachment = videoAttachment;
    }

    public String getImageAttachment() {
        return imageAttachment;
    }

    public void setImageAttachment(String imageAttachment) {
        this.imageAttachment = imageAttachment;
    }
}
