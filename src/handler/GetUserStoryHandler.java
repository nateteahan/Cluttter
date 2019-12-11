package handler;

import DatabaseAccess.StoryDAO;
import com.amazonaws.services.lambda.runtime.Context;
import request.GetUserStoryRequest;
import response.GetUserStoryResponse;

public class GetUserStoryHandler {
    public GetUserStoryResponse handleUserStoryRequest(GetUserStoryRequest request, Context context) {

        StoryDAO storyDAO = new StoryDAO();
        GetUserStoryResponse response = storyDAO.getUserStory(request, request.getLastKey(), context);

        return response;
    }
}
