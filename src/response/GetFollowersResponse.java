package response;

import Model.FollowInfo;

import java.util.List;

public class GetFollowersResponse {
    public List<FollowInfo> followers;
    public String message;
    public String lastKey;

    public GetFollowersResponse(List<FollowInfo> followers, String message, String lastKey) {
        this.followers = followers;
        this.message = message;
        this.lastKey = lastKey;
    }

    public List<FollowInfo> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowInfo> followers) {
        this.followers = followers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastKey() {
        return lastKey;
    }

    public void setLastKey(String lastKey) {
        this.lastKey = lastKey;
    }
}
