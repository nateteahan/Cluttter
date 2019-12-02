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
        return response;
    }
}
