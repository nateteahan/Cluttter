package handler;

import DatabaseAccess.FollowDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.UnfollowUserRequest;
import response.FollowUserResponse;
import response.UnfollowUserResponse;

public class UnfollowUserHandler {
    public UnfollowUserResponse handleUnfollowRequest(UnfollowUserRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Entering Unfollow Function");

        FollowDAO followDAO = new FollowDAO();
        String message = followDAO.unfollowUser(request);

        UnfollowUserResponse response = new UnfollowUserResponse(message);

        return response;
    }
}
