package Model;

public class Status {
    public String profilePic;
    public String firstName;
    public String userHandle;
    public String time;
    public String status;
    public String imageAttachment;
    public String videoAttachment;

    public Status(String profilePic, String firstName, String userHandle, String time, String status, String imageAttachment, String videoAttachment) {
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.userHandle = userHandle;
        this.time = time;
        this.status = status;
        this.imageAttachment = imageAttachment;
        this.videoAttachment = videoAttachment;
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

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImageAttachment() {
        return imageAttachment;
    }

    public void setImageAttachment(String imageAttachment) {
        this.imageAttachment = imageAttachment;
    }

    public String getVideoAttachment() {
        return videoAttachment;
    }

    public void setVideoAttachment(String videoAttachment) {
        this.videoAttachment = videoAttachment;
    }
}
