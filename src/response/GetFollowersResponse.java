package response;

import Model.FollowInfo;

import java.util.List;

public class GetFollowersResponse {
    public List<FollowInfo> followers;
    public String message;

    public GetFollowersResponse(List<FollowInfo> followers, String message) {
        this.followers = followers;
        this.message = message;
    }
}
