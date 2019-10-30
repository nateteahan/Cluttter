package Response;

import com.example.clutter.Model.FollowInfo;

import java.util.List;

public class FollowersResponse {
    List<FollowInfo> followers;
    String message;

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
}
