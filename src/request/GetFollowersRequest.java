package request;

public class GetFollowersRequest {
    public String userhandle;
    public String lastFollower;

    public String getUserhandle() {
        return userhandle;
    }

    public void setUserhandle(String userhandle) {
        this.userhandle = userhandle;
    }

    public String getLastFollower() {
        return lastFollower;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }
}
