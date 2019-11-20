package handler;

import Model.FollowInfo;
import request.GetFollowersRequest;
import response.GetFollowersResponse;

import java.util.ArrayList;

public class GetFollowersHandler {
    private ArrayList<FollowInfo> followers;

    public GetFollowersResponse getFollowerHandler(GetFollowersRequest request) {
        followers = new ArrayList<>();

        FollowInfo follower = new FollowInfo("https://www.famousbirthdays.com/faces/hunt-director-justin-image.jpg","@justinj_hunt");
        followers.add(follower);

        follower = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "@roscoeevans");
        followers.add(follower);

        GetFollowersResponse response = new GetFollowersResponse(followers, null);

        return response;
    }
}
