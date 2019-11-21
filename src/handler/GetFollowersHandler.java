package handler;

import DatabaseAccess.FollowDAO;
import Model.FollowInfo;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.GetFollowersRequest;
import response.GetFollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class GetFollowersHandler {
    private List<FollowInfo> followers;

    public GetFollowersResponse getFollowerHandler(GetFollowersRequest request, Context context) {
        LambdaLogger logger = context.getLogger();

        FollowDAO followDAO = new FollowDAO();
        logger.log("Entering FollowDAO");
        GetFollowersResponse response = followDAO.getFollowers(request, context);
        //
//        FollowInfo follower = new FollowInfo("https://www.famousbirthdays.com/faces/hunt-director-justin-image.jpg","@justinj_hunt");
//        followers.add(follower);
//
//        follower = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "@roscoeevans");
//        followers.add(follower);

//        GetFollowersResponse response = new GetFollowersResponse(followers, null);

        return response;
    }
}
