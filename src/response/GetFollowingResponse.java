package response;

import Model.FollowInfo;

import java.util.List;

public class GetFollowingResponse {
    public List<FollowInfo> following;
    public String message;
    public String lastKey;

    public GetFollowingResponse(List<FollowInfo> following, String message, String lastKey) {
        this.following = following;
        this.message = message;
        this.lastKey = lastKey;
    }

    public List<FollowInfo> getFollowing() {
        return following;
    }

    public void setFollowing(List<FollowInfo> following) {
        this.following = following;
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
