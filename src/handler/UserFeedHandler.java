package handler;

import DatabaseAccess.FeedDAO;
import Model.Status;
import com.amazonaws.services.lambda.runtime.Context;
import request.GetUserFeedRequest;
import response.GetUserFeedResponse;

import java.util.ArrayList;

public class UserFeedHandler {
    public GetUserFeedResponse handleFeedRequest(GetUserFeedRequest request, Context context) {

        FeedDAO feedDAO = new FeedDAO();//
//        GetUserFeedResponse response = feedDAO.getUserFeed(request);//

        GetUserFeedResponse response = feedDAO.FEED(request, request.getLastKey(), context);

        return response;
    }
}
