package request;

public class GetFollowingRequest {
    public String userhandle;
    public String lastFollowee;

    public String getUserhandle() {
        return userhandle;
    }

    public void setUserhandle(String userhandle) {
        this.userhandle = userhandle;
    }

    public String getLastFollowee() {
        return lastFollowee;
    }

    public void setLastFollowee(String lastFollowee) {
        this.lastFollowee = lastFollowee;
    }
}
