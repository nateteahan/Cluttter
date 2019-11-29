package request;

public class IsFollowingRequest {
    public String followerHandle;
    public String followeeHandle;

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
}
