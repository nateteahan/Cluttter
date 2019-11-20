package request;

public class FollowUserRequest {
    public String followerHandle;           //Do I need this to get the handle of the followee from the URL?
    public String followeeHandle;
//    public String followerName;
//    public String followeeName;

    public String getFollowerHandle() {
        return followerHandle;
    }

    public void setFollowerHandle(String followerHandle) {
        this.followerHandle = followerHandle;
    }

    public String getFolloweeHandle() {
        return followeeHandle;
    }

    public void setFolloweeHandle(String followeeHandle) {
        this.followeeHandle = followeeHandle;
    }

//    public String getFollowerName() {
//        return followerName;
//    }
//
//    public void setFollowerName(String followerName) {
//        this.followerName = followerName;
//    }
//
//    public String getFolloweeName() {
//        return followeeName;
//    }
//
//    public void setFolloweeName(String followeeName) {
//        this.followeeName = followeeName;
//    }
}
