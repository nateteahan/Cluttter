package handler;

import DatabaseAccess.StoryDAO;
import Model.Status;
import com.amazonaws.services.lambda.runtime.Context;
import request.GetUserFeedRequest;
import request.GetUserStoryRequest;
import response.GetUserResponse;
import response.GetUserStoryResponse;

import java.util.ArrayList;
import java.util.List;

public class GetUserStoryHandler {
    public GetUserStoryResponse handleUserStoryRequest(GetUserStoryRequest request, Context context) {
//        List<Status> story = new ArrayList<>();
//        Status status;
//
//        status = new Status("http://i.imgur.com/bIRGzVO.jpg","Rocky", "@roscoe_evans", "6:53 p.m.", "My #CS 312 project ruined me today", "https://bangladhol.com/book_th/C29B5E81.jpg", null);
//        story.add(status);
//
//        GetUserStoryResponse response = new GetUserStoryResponse(story, null);

        StoryDAO storyDAO = new StoryDAO();
        GetUserStoryResponse response = storyDAO.STORY(request, request.getLastKey(), context);

        return response;
    }
}
