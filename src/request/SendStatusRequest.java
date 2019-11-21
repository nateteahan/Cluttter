package request;

public class SendStatusRequest {
    public String profilePic;
    public String firstName;
    public String userHandle;
    public String status;
    public String pictureAttachment;
    public String videoAttachment;
    public String time;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPictureAttachment() {
        return pictureAttachment;
    }

    public void setPictureAttachment(String pictureAttachment) {
        this.pictureAttachment = pictureAttachment;
    }

    public String getVideoAttachment() {
        return videoAttachment;
    }

    public void setVideoAttachment(String videoAttachment) {
        this.videoAttachment = videoAttachment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
