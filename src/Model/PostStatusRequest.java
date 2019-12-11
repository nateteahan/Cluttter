package Model;

import request.SendStatusRequest;

import java.util.List;

public class PostStatusRequest {
    public List<FollowInfo> followers;
    public SendStatusRequest status;

    public List<FollowInfo> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowInfo> followers) {
        this.followers = followers;
    }

    public SendStatusRequest getStatus() {
        return status;
    }

    public void setStatus(SendStatusRequest status) {
        this.status = status;
    }
}
