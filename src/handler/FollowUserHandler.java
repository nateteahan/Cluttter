package handler;

import DatabaseAccess.FollowDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.FollowUserRequest;
import response.FollowUserResponse;

public class FollowUserHandler {
    public FollowUserResponse handleFollowRequest(FollowUserRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Entering Follow Function");

        FollowDAO followDAO = new FollowDAO();
        String message = followDAO.followUser(request);

        FollowUserResponse response = new FollowUserResponse(message);
        return response;
    }
}
