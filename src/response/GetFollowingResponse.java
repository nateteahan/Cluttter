package response;

import Model.FollowInfo;

import java.util.List;

public class GetFollowingResponse {
    public List<FollowInfo> following;
    public String message;

    public GetFollowingResponse(List<FollowInfo> following, String message) {
        this.following = following;
        this.message = message;
    }
}
