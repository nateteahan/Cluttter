package handler;

import DatabaseAccess.FeedDAO;
import com.amazonaws.services.lambda.runtime.Context;
import request.GetUserFeedRequest;
import response.GetUserFeedResponse;

public class UserFeedHandler {
    public GetUserFeedResponse handleFeedRequest(GetUserFeedRequest request, Context context) {

        FeedDAO feedDAO = new FeedDAO();

        GetUserFeedResponse response = feedDAO.getUserFeed(request, request.getLastKey(), context);

        return response;
    }
}
