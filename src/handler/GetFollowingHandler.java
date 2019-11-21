package handler;

import Model.FollowInfo;
import request.GetFollowingRequest;
import response.GetFollowingResponse;

import java.util.ArrayList;

public class GetFollowingHandler {
    private ArrayList<FollowInfo> following;

    public GetFollowingResponse getFollowingHandler(GetFollowingRequest request) {
        following = new ArrayList<>();

//        FollowInfo followee = new FollowInfo("https://hips.hearstapps.com/ghk.h-cdn.co/assets/17/30/2560x1280/landscape-1500925839-golden-retriever-puppy.jpg?resize=480:*", "@Dog");
//        following.add(followee);

        FollowInfo followee = new FollowInfo("@Dog");
        following.add(followee);

        GetFollowingResponse response = new GetFollowingResponse(following, null);

        return response;
    }
}
