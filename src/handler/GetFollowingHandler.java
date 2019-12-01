package handler;

import DatabaseAccess.FollowDAO;
import Model.FollowInfo;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.GetFollowingRequest;
import response.GetFollowingResponse;

import java.util.ArrayList;

public class GetFollowingHandler {
    private ArrayList<FollowInfo> following;

    public GetFollowingResponse getFollowingHandler(GetFollowingRequest request, Context context) {
        following = new ArrayList<>();

        FollowDAO followDAO = new FollowDAO();
        LambdaLogger logger = context.getLogger();
        logger.log("Getting following");

        GetFollowingResponse response = followDAO.getFollowing(request, context, request.getLastFollowee());
//        FollowInfo followee = new FollowInfo("https://hips.hearstapps.com/ghk.h-cdn.co/assets/17/30/2560x1280/landscape-1500925839-golden-retriever-puppy.jpg?resize=480:*", "@Dog");
//        following.add(followee);

//        FollowInfo followee = new FollowInfo("@Dog");
//        following.add(followee);
//
//        GetFollowingResponse response = new GetFollowingResponse(following, null);

        return response;
    }
}
