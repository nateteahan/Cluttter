package Model;

public class FollowInfo {
//    public String profilePic;
    public String userHandle;

//    public FollowInfo(String profilePic, String handle) {
////        this.profilePic = profilePic;
//        this.userHandle = handle;
//    }

    public FollowInfo(String userHandle) {
        this.userHandle = userHandle;
    }

//    public String getProfilePic() {
////        return profilePic;
////    }
////
////    public void setProfilePic(String profilePic) {
////        this.profilePic = profilePic;
////    }

    public String getUserHandle() {
        return userHandle;
    }

    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }
}
